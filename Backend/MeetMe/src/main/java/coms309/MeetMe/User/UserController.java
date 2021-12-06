package coms309.MeetMe.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import coms309.MeetMe.Availability.Availability;
import coms309.MeetMe.Availability.AvailabilityRepository;
import coms309.MeetMe.FriendRequest.*;
import coms309.MeetMe.PushNotifications.service.PushNotificationService;
import coms309.MeetMe.Stringy.Stringy;


import java.util.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AvailabilityRepository availabilityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    private PushNotificationService pushNotificationService;

    public UserController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }


    @GetMapping(value = "/", produces = "application/json")
    public List<UserShadow> getAllUsers() {
        return UserShadow.build(userRepository.findAll());
    }

    @PostMapping(value = "/", produces = "application/json")
    public String createUser(@RequestBody User user) {
        if (user == null)
            return Stringy.error("Invalid request body");

        User checkIfExists = userRepository.findByName(user.getName());

        if (checkIfExists != null)
            return Stringy.error("User already exists");

        Availability avail = new Availability(new boolean[168], user);
        user.setAvailability(avail);

        availabilityRepository.save(avail);
        userRepository.save(user);
        return Stringy.success();
    }


    @GetMapping(value = "/id/{id}", produces = "application/json")
    public UserShadow getUserById(@PathVariable int id) {
        return new UserShadow(userRepository.findById(id));
    }


    @GetMapping(value = "/name/{name}", produces = "application/json")
    public UserShadow getUserByName(@PathVariable String name) {
        return new UserShadow(userRepository.findByName(name));
    }

    @PostMapping(path = "/login", produces = "application/json")
    public String loginUser(@RequestBody User user) {

        if (user == null) return Stringy.error("Null user");

        User temp = userRepository.findByName(user.getName());

        if (temp == null) return Stringy.error("User not found");

        if (temp.getPassword().equals(user.getPassword())) return Stringy.success();
        
        return Stringy.error("Invalid credentials");
    }


    @PostMapping(path = "/changePassword", produces = "application/json")
    public String changePassword(@RequestBody User user) {
        if (user == null) return Stringy.error("Null user");

        User temp = userRepository.findByName(user.getName());

        if (temp == null) return Stringy.error("User not found");

        temp.setPassword(user.getPassword());
        userRepository.save(temp);

        return Stringy.success();
    }


    @GetMapping(value = "/search/{name}", produces = "application/json")
    public List<UserShadow> getSearch(@PathVariable String name) {
        return UserShadow.build(userRepository.findBySearch(name));
    }


    //---- Friends ----


    @GetMapping(path = "/{name}/friends", produces = "application/json")
    public List<UserShadow> getFriends(@PathVariable String name) {
        return UserShadow.build(userRepository.findByName(name).getFriends());
    }


    @GetMapping(path = "/{name}/friendRequestsSent", produces = "application/json")
    public List<UserShadow> getFriendRequestsSent(@PathVariable String name) {
        User me = userRepository.findByName(name);

        if (me == null) return null;

        List<FriendRequest> friendRequests = friendRequestRepository.findSent(me.getId());

        List<User> users = new ArrayList<User>();
        for (int i = 0; i < friendRequests.size(); i++) {
            if (friendRequests.get(i).getState() != FriendRequestState.PENDING) continue;
            users.add(friendRequests.get(i).getUserB());
        }
        return UserShadow.build(users);
    }


    @GetMapping(path = "/{name}/friendRequestsReceived", produces = "application/json")
    public List<UserShadow> getFriendRequestsReceived(@PathVariable String name) {
        User me = userRepository.findByName(name);

        if (me == null) return null;

        List<FriendRequest> friendRequests = friendRequestRepository.findReceived(me.getId());

        List<User> users = new ArrayList<User>();
        for (int i = 0; i < friendRequests.size(); i++) {
            if (friendRequests.get(i).getState() != FriendRequestState.PENDING) continue;
            users.add(friendRequests.get(i).getUserA());
        }
        return UserShadow.build(users);
    }


    @PostMapping(path ="/sendFriendRequest", produces = "application/json")
    public String sendFriendRequest(@RequestBody UserNamePair userNamePair) {

        if (userNamePair.isInvalid()) return Stringy.error("User was not found");

        User userA = userRepository.findByName(userNamePair.getUserNameA());
        User userB = userRepository.findByName(userNamePair.getUserNameB());

        if (userA == null || userB == null) return Stringy.error("User was not found");

        if ((userA.getFriends() != null && userA.getFriends().contains(userB)) || 
            (userB.getFriends() != null && userB.getFriends().contains(userA))) return Stringy.error("Already friends");
        
        FriendRequest friendRequest = friendRequestRepository.findByUsers(userA.getId(), userB.getId());
        if (friendRequest == null) friendRequest = friendRequestRepository.findByUsers(userB.getId(), userA.getId());

        // Friend request already exists
        if (friendRequest != null) {

            if (friendRequest.getState() == FriendRequestState.PENDING) return Stringy.error("Already sent");
            
            friendRequest.reset();
        }
        else {

            friendRequest = new FriendRequest(userA, userB, FriendRequestState.PENDING);
    
            userA.sendFriendRequest(friendRequest);
            userB.receiveFriendRequest(friendRequest);
            userRepository.save(userA);
            userRepository.save(userB);
        }
        
        friendRequestRepository.save(friendRequest);
        
        // If user found, send out friend request notification to user's token
        // pushNotificationService.sendPushNotificationToToken("title", "message", Topic.COMMON, "putTokenHere");
        
        // Currently this sends to every user, we need to save user tokens during registration.
        pushNotificationService.sendPushNotification("Friend request", 
            "You have a friend request from " + userA.getName()+ "!", 
            userB.getName());
        
        return Stringy.success();
    }


    @PostMapping(path ="/acceptFriendRequestNames", produces = "application/json")
    public String acceptFriendRequestNames(@RequestBody UserNamePair userNamePair) {

        if (userNamePair.isInvalid()) return Stringy.error("User was not found");

        User userA = userRepository.findByName(userNamePair.getUserNameA());
        User userB = userRepository.findByName(userNamePair.getUserNameB());

        if (userA == null || userB == null) return Stringy.error("User was not found");

        FriendRequest friendRequest = friendRequestRepository.findByUsers(userA.getId(), userB.getId());
        if (friendRequest == null) friendRequest = friendRequestRepository.findByUsers(userB.getId(), userA.getId());
        
        return acceptFriendRequestCommon(friendRequest);
    }

    @PostMapping(path ="/acceptFriendRequestId", produces = "application/json")
    public String acceptFriendRequestId(@RequestBody int id) {
        return acceptFriendRequestCommon(friendRequestRepository.findById(id));
    }

    private String acceptFriendRequestCommon(FriendRequest friendRequest) {
        if (friendRequest == null) return Stringy.error("Friend Request was not found");

        if (friendRequest.getState() != FriendRequestState.PENDING) return Stringy.error("Friend request already finalized");

        // Save friend in database
        friendRequest.getUserA().addFriend(friendRequest);
        friendRequest.getUserB().addFriend(friendRequest);
        friendRequest.accept();

        userRepository.save(friendRequest.getUserA());
        userRepository.save(friendRequest.getUserB());
        friendRequestRepository.save(friendRequest);

        // Notify other user
        pushNotificationService.sendPushNotification("New Friend", friendRequest.getUserB().getName() + "accepted your friend request!", friendRequest.getUserA().getName());

        return Stringy.success();
    }

    @PostMapping(path ="/rejectFriendRequestNames", produces = "application/json")
    public String rejectFriendRequestNames(@RequestBody UserNamePair userNamePair) {
        
        if (userNamePair.isInvalid()) return Stringy.error("User was not found");

        User userA = userRepository.findByName(userNamePair.getUserNameA());
        User userB = userRepository.findByName(userNamePair.getUserNameB());

        if (userA == null || userB == null) return Stringy.error("User was not found");
        
        FriendRequest friendRequest = friendRequestRepository.findByUsers(userA.getId(), userB.getId());
        if (friendRequest == null) friendRequest = friendRequestRepository.findByUsers(userB.getId(), userA.getId());
        
        return rejectFriendRequestCommon(friendRequest);
    }

    @PostMapping(path ="/rejectFriendRequestId", produces = "application/json")
    public String rejectFriendRequestId(@RequestBody int id) {
        return rejectFriendRequestCommon(friendRequestRepository.findById(id));
    }

    private String rejectFriendRequestCommon(FriendRequest friendRequest) {
        if (friendRequest == null) return Stringy.error("Friend Request was not found");

        if (friendRequest.getState() != FriendRequestState.PENDING) return Stringy.error("Friend request already finalized");
        
        friendRequest.reject();

        friendRequestRepository.save(friendRequest);

        int friendCount = friendRequest.getUserB().getFriends().size();

        pushNotificationService.sendPushNotification("Rejected", friendRequest.getUserB().getName() + "rejected your friend request! you now have " + friendCount + "friend(s).", friendRequest.getUserA().getName());

        return Stringy.success();
    }
}
