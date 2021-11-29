package coms309.MeetMe.User;

// import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import coms309.MeetMe.PushNotifications.model.Topic;
import coms309.MeetMe.PushNotifications.service.PushNotificationService;
import coms309.MeetMe.Stringy.Stringy;


import java.util.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    private PushNotificationService pushNotificationService;

    public UserController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }


    @GetMapping(value = "/", produces = "application/json")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @GetMapping(value = "/id/{id}", produces = "application/json")
    User getUserById(@PathVariable int id) {
        return userRepository.findById(id);
    }


    @GetMapping(value = "/name/{name}", produces = "application/json")
    User getUserByName(@PathVariable String name) {
        return userRepository.findByName(name);
    }


    @PostMapping(value = "/{name}/availability", produces = "application/json")
    String setAvailability(@PathVariable String name, @RequestBody boolean[] availability) {
        User temp = userRepository.findByName(name);
        if (temp == null) {
            return Stringy.error("Invalid request body");
        }
        temp.setAvailability(availability);
        userRepository.save(temp);
        return Stringy.success();
    }


    @PostMapping(value = "/", produces = "application/json")
    String createUser(@RequestBody User user) {
        if (user == null)
            return Stringy.error("Invalid request body");

        User checkIfExists = userRepository.findByName(user.getName());

        if (checkIfExists != null)
            return Stringy.error("User already exists");


        userRepository.save(user);
        return Stringy.success();
    }


    @PostMapping(path = "/login", produces = "application/json")
    String loginUser(@RequestBody User user) {
        if (user != null) {
            User temp = userRepository.findByName(user.getName());
            if (temp != null && temp.getPassword().equals(user.getPassword())) {
                return Stringy.success();
            }
        }
        return Stringy.error("Invalid credentials");
    }

    // ---- Friends ----

    @GetMapping(path = "/{name}/getFriends", produces = "application/json")
    public Set<User> getFriends(@PathVariable String name) {
        return userRepository.findByName(name).getFriends();
    }

    @GetMapping(path = "/{name}/getFriendRequestsSent", produces = "application/json")
    public Set<User> getFriendRequestsSend(@PathVariable String name) {
        User me = userRepository.findByName(name);
        List<FriendRequest> friendRequests = friendRequestRepository.findFriendRequestsSent(me.getId());
        Set<User> users = new HashSet<User>();
        for(int i = 0; i < friendRequests.size(); i++) {
            users.add(friendRequests.get(i).getUserB());
        }
        return users;
    }

    @GetMapping(path = "/{name}/getFriendRequestsReceived", produces = "application/json")
    public Set<User> getFriendRequestsReceived(@PathVariable String name) {
        User me = userRepository.findByName(name);
        List<FriendRequest> friendRequests = friendRequestRepository.findFriendRequestsReceived(me.getId());
        Set<User> users = new HashSet<User>();
        for(int i = 0; i < friendRequests.size(); i++) {
            users.add(friendRequests.get(i).getUserA());
        }
        return users;
    }

    @PostMapping(path ="/addFriendRequest", produces = "application/json")
    public String addFriendRequest(@RequestBody UserNamePair userNamePair) {

        FriendRequest friendRequest = friendRequestRepository.findByNames(userNamePair.getUserNameA(), userNamePair.getUserNameB());

        // Friend request already exists
        if (friendRequest != null) {
            friendRequest.reset();
        }
        else {
            friendRequest = getUsers(userNamePair);

            if (friendRequest == null) return Stringy.error("User was not found");
    
            User userA = friendRequest.getUserA(), userB = friendRequest.getUserB();
    
            // Save friend request in database (Not currently working) (but should work now)
            userA.sendFriendRequest(friendRequest);
            userB.receiveFriendRequest(friendRequest);
            userRepository.save(userA);
            userRepository.save(userB);
        }
        
        friendRequestRepository.save(friendRequest);
        
        // If user found, send out friend request notification to user's token
        // pushNotificationService.sendPushNotificationToToken("title", "message", Topic.COMMON, "putTokenHere");
        
        // Currently this sends to every user, we need to save user tokens during registration.
        pushNotificationService.sendPushNotification(friendRequest.getUserA().getName(), "You have a friend request from " + friendRequest.getUserB().getName()+ "!", Topic.COMMON);
        
        return Stringy.success();
    }

    @PostMapping(path ="/acceptFriendRequest", produces = "application/json")
    public String acceptFriendRequest(@RequestBody UserNamePair userNamePair) {
        
        FriendRequest friendRequest = friendRequestRepository.findByNames(userNamePair.getUserNameA(), userNamePair.getUserNameB());
        
        if (friendRequest == null) return Stringy.error("Friend Request was not found");

        // Save friend in database
        friendRequest.getUserA().addFriend(friendRequest);
        friendRequest.getUserB().addFriend(friendRequest);
        friendRequest.accept();

        userRepository.save(friendRequest.getUserA());
        userRepository.save(friendRequest.getUserB());
        friendRequestRepository.save(friendRequest);

        // Notify other user
        pushNotificationService.sendPushNotification(friendRequest.getUserA().getName(), friendRequest.getUserB().getName() + "accepted your friend request!", Topic.COMMON);

        return Stringy.success();
    }


    @PostMapping(path ="/rejectFriendRequest", produces = "application/json")
    public String rejectFriendRequest(@RequestBody UserNamePair userNamePair) {
        
        FriendRequest friendRequest = friendRequestRepository.findByNames(userNamePair.getUserNameA(), userNamePair.getUserNameB());
        
        if (friendRequest == null) return Stringy.error("Friend Request was not found");

        friendRequest.reject();

        friendRequestRepository.save(friendRequest);

        return Stringy.success();
    }

    private FriendRequest getUsers(UserNamePair userNamePair) {
        if (userNamePair.isInvalid()) return null;

        // Lookup sending user
        User userA = userRepository.findByName(userNamePair.getUserNameA());

        // Lookup receiving user
        User userB = userRepository.findByName(userNamePair.getUserNameB());

        if (userA == null || userA == null) return null;

        FriendRequest friendRequest = new FriendRequest(userA, userB, FriendRequestState.PENDING);

        return friendRequest;
    }

}


