package coms309.MeetMe.User;

// import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import coms309.MeetMe.PushNotifications.model.Topic;
// import coms309.MeetMe.PushNotifications.model.Topic;
import coms309.MeetMe.PushNotifications.service.PushNotificationService;
import coms309.MeetMe.Stringy.Stringy;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

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


    @GetMapping(path = "/{name}/getFriends", produces = "application/json")
    public Set<User> getFriends(@PathVariable String name) {
        return userRepository.findByName(name).getFriends();
    }


    // @PostMapping(path = "/addFriend", produces = "application/json")
    // public String addFriend(@RequestBody Friend friend) {
    //     int senderId = friend.getSenderId();
    //     int receiverId = friend.getRecieverId();
    //     User self = userRepository.findById(senderId);
    //     User friend = userRepository.findById(receiverId);
    //     if (self != null && friend != null & senderId != receiverId) {
    //         self.addFriend(friend);
    //         friend.addFriend(self);
    //         userRepository.save(self);
    //         userRepository.save(friend);
    //         return Stringy.success();
    //     }
    //     return Stringy.error("Invalid request body");
    // }

    // @PostMapping(path = "/deleteFriendRequest", produces = "application/json")
    // public String deleteFriendRequestbyNames(@RequestBody FriendShip s) {
    //     int senderId = s.getSenderId();
    //     int receiverId = s.getRecieverId();
    //     User self = userRepository.findById(senderId);
    //     User friend = userRepository.findById(receiverId);
    //     if (senderId != receiverId && self != null && friend != null) {
    //         userRepository.deleteFriendRequest(self.getId(), friend.getId());

    //         return Stringy.success();
    //     }
    //     return Stringy.error("Invalid request body");
    // }



    // @PostMapping(path = "/addFriendRequest", produces = "application/json")
    // public String addFriendRequest(@RequestBody FriendShip s) {
    //     int senderId = s.getSenderId();
    //     int receiverId = s.getRecieverId();
    //     User self = userRepository.findById(senderId);
    //     User friend = userRepository.findById(receiverId);
    //     if (self != null && friend != null && senderId != receiverId) {
    //         self.addFriendRequest(friend);
    //         userRepository.save(friend);
    //         return Stringy.success();
    //     }
    //     return Stringy.error("Invalid request body");

    // }


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
    
            // Save friend request in database (Not currently working)
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



    // TODO: only 1 variable is allowed in @RequestBody 
    // @PostMapping(path ="/deleteFriendRequest", produces = "application/json")
    // public String deleteFriendRequestbyIds(@RequestBody int self, int f){
    //     userRepository.deleteFriendRequest(self, f);
    //     userRepository.deleteFriendRequest(f, self);
    //     return Stringy.success();
    // }

    // @PostMapping(path ="/addFriendRequest", produces = "application/json")
    // public String addFriendRequest(@RequestBody User name, @RequestBody User fName) {
    //     if(name != null && fName != null && !name.equals(fName)) {
    //         name.addFriendRequest(fName);
    //         fName.addFriendRequest(name);
    //         userRepository.save(name);
    //         userRepository.save(fName);
    //         return Stringy.success();
    //     }
    //     return Stringy.error("Invalid request body");
    // }


//    @PutMapping("/users/{id}")
//    User updateUser(@PathVariable int id, @RequestBody User request){
//        User user = userRepository.findById(id);
//        if(user == null)
//            return null;
//        userRepository.save(request);
//        return userRepository.findById(id);
//    }

//    @PutMapping("/users/{userId}/laptops/{laptopId}")
//    String assignLaptopToUser(@PathVariable int userId,@PathVariable int laptopId){
//        User user = userRepository.findById(userId);
//        Laptop laptop = laptopRepository.findById(laptopId);
//        if(user == null || laptop == null)
//            return Stringy.error("Invalid request body");
//        laptop.setUser(user);
//        user.setLaptop(laptop);
//        userRepository.save(user);
//        return Stringy.success();
//    }

//    @DeleteMapping(path = "/users/{id}")
//    String deleteLaptop(@PathVariable int id){
//        userRepository.deleteById(id);
//        return Stringy.success();
//    }


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


