
package coms309.MeetMe.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import coms309.MeetMe.PushNotifications.model.Topic;
import coms309.MeetMe.PushNotifications.service.PushNotificationService;
import coms309.MeetMe.Stringy.Stringy;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private PushNotificationService pushNotificationService;

    public UserController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    private String success = "{\"message\":\"Success\"}";
    private String failure = "{\"message\":\"User not found\"}";


    @GetMapping(value = "/", produces = "application/json")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }


    @GetMapping(value = "/id/{id}", produces = "application/json")
    User getUserById( @PathVariable int id) {
        return userRepository.findById(id);
    }


    @GetMapping(value = "/name/{name}", produces = "application/json")
    User getUserByName( @PathVariable String name) {
        return userRepository.findByName(name);
    }





    @PostMapping(value = "/", produces = "application/json")
    String createUser(@RequestBody User user) {
        if (user == null)
            return failure;

        User checkIfExists = userRepository.findByName(user.getName());

        if (checkIfExists != null) 
            return "{\"message\":\"Username taken\"}";

        userRepository.save(user);
        return success;
    }

  /*  @GetMapping(path = "/login", produces = "application/json")
    String loginUser(@RequestParam String name,@RequestParam  String password ) {
        User temp = userRepository.findByName(name);
        if(temp != null) {
            if (temp.getPassword().equals(password)) {
                return success;
            }
        }
        return failure;
    }
*/

    @PostMapping(path = "/login", produces = "application/json")
    String loginUser(@RequestBody User user ) {
        if(user != null){
           User temp = userRepository.findByName(user.getName());
           if (temp != null && temp.getPassword().equals(user.getPassword())) {
                   return success;
           }
        }
        return failure;
    }


    @GetMapping(path = "/{name}/getFriends", produces = "application/json")
    public Set<User> getFriends (@PathVariable String name) {
        return userRepository.findByName(name).getFriends();
    }


    @PostMapping(path ="/addFriendRequest", produces = "application/json")
    public String addFriendRequest(@RequestBody UserNamePair userNamePair) {

        User[] users = getUsers(userNamePair);

        if (users == null) return Stringy.error("User was not found");

        // Save friend request in database (Not currently working)
        // userA.addFriendRequest(userB);
        // userB.addFriendRequest(userA);
        // userRepository.save(userA);
        // userRepository.save(userB);
        
        // If user found, send out friend request notification to user's token
        // pushNotificationService.sendPushNotificationToToken("title", "message", Topic.COMMON, "putTokenHere");
        
        // Currently this sends to every user, we need to save user tokens during registration.
        pushNotificationService.sendPushNotification(users[0].getName(), "You have a friend request from " + users[1].getName()+ "!", Topic.COMMON);
        
        return Stringy.success();
    }


    @PostMapping(path ="/acceptFriendRequest", produces = "application/json")
    public String acceptFriendRequest(@RequestBody UserNamePair userNamePair) {

        User[] users = getUsers(userNamePair);

        if (users == null) return Stringy.error("User was not found");

        // Friend request not found
        if (!users[0].getFriendRequests().contains(users[1]) || !users[1].getFriendRequests().contains(users[0])) return Stringy.error("Friend request not found");

        // Save friend in database
        users[0].addFriend(users[1]);
        users[1].addFriend(users[0]);
        users[0].removeFriendRequest(users[1]);
        users[1].removeFriendRequest(users[0]);
        userRepository.save(users[0]);
        userRepository.save(users[1]);

        return Stringy.success();
    }


    @PostMapping(path ="/rejectFriendRequest", produces = "application/json")
    public String rejectFriendRequest(@RequestBody UserNamePair userNamePair) {

        User[] users = getUsers(userNamePair);

        if (users == null) return Stringy.error("User was not found");

        // Friend request not found
        if (!users[0].getFriendRequests().contains(users[1]) || !users[1].getFriendRequests().contains(users[0])) return Stringy.error("Friend request not found");

        // Remove friend request from database
        users[0].removeFriendRequest(users[1]);
        users[1].removeFriendRequest(users[0]);

        return Stringy.success();
    }



    // TODO: only 1 variable is allowed in @RequestBody 
    // @PostMapping(path ="/deleteFriendRequest", produces = "application/json")
    // public String deleteFriendRequestbyIds(@RequestBody int self, int f){
    //     userRepository.deleteFriendRequest(self, f);
    //     userRepository.deleteFriendRequest(f, self);
    //     return success;
    // }

    // @PostMapping(path ="/addFriendRequest", produces = "application/json")
    // public String addFriendRequest(@RequestBody User name, @RequestBody User fName) {
    //     if(name != null && fName != null && !name.equals(fName)) {
    //         name.addFriendRequest(fName);
    //         fName.addFriendRequest(name);
    //         userRepository.save(name);
    //         userRepository.save(fName);
    //         return success;
    //     }
    //     return failure;
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
//            return failure;
//        laptop.setUser(user);
//        user.setLaptop(laptop);
//        userRepository.save(user);
//        return success;
//    }

//    @DeleteMapping(path = "/users/{id}")
//    String deleteLaptop(@PathVariable int id){
//        userRepository.deleteById(id);
//        return success;
//    }


    private User[] getUsers(UserNamePair userNamePair) {
        if (userNamePair.isInvalid()) return null;

        // Lookup sending user
        User userA = userRepository.findByName(userNamePair.getUserNameA());

        // Lookup receiving user
        User userB = userRepository.findByName(userNamePair.getUserNameB());

        if (userA == null || userA == null) return null;

        return new User[] { userA, userB };
    }


}


