package coms309.MeetMe.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/{name}/availability", produces = "application/json")
    String setAvailability(@PathVariable String name, @RequestBody boolean [] availability ){
        User temp =  userRepository.findByName(name);
        if(temp ==null){
            return failure;
        }
        temp.setAvailability(availability);
        userRepository.save(temp);
        return success;
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
            if(temp != null && temp.getPassword().equals(user.getPassword())){
                return success;
            }
        }
        return failure;
    }
    @GetMapping(path = "/{name}/getFriends", produces = "application/json")
    public Set<User> getFriends (@PathVariable String name) {
        return userRepository.findByName(name).getFriends();
    }

    @PostMapping(path = "/addFriend", produces = "application/json")
    public String addFriend(@RequestBody FriendShip s) {
        int senderId = s.getSenderId();
        int receiverId = s.getRecieverId();
        User self = userRepository.findById(senderId);
        User friend = userRepository.findById(receiverId);
        if(self != null && friend != null & senderId != receiverId){
            self.addFriend(friend);
            friend.addFriend(self);
            userRepository.save(self);
            userRepository.save(friend);
            return success;
        }
        return failure;
    }




    @PostMapping(path ="/deleteFriendRequest", produces = "application/json")

    public String deleteFriendRequestbyNames(@RequestBody FriendShip s){
        int senderId = s.getSenderId();
        int receiverId = s.getRecieverId();
        User self = userRepository.findById(senderId);
        User friend = userRepository.findById(receiverId);
        if(senderId != receiverId && self != null && friend != null) {
            userRepository.deleteFriendRequest(self.getId(), friend.getId());

            return success;
        }
        return failure;
    }



        @PostMapping(path = "/addFriendRequest", produces = "application/json")
        public String addFriendRequest(@RequestBody FriendShip s) {
        int senderId = s.getSenderId();
        int receiverId = s.getRecieverId();
        User self = userRepository.findById(senderId);
        User friend = userRepository.findById(receiverId);
        if(self != null && friend != null && senderId != receiverId){
            int i = friend.getId();
            self.addFriendRequest(friend);
            userRepository.save(friend);
            return success;
        }
        return failure;
    }

    @GetMapping(path = "/{name}/getFriendRequest", produces = "application/json")
    public Set<User> getFriendRequest (@PathVariable String name) {
        return userRepository.findByName(name).getFriendReQuest();
    }

    @GetMapping(path = "/{name}/getFriendRequestFrom", produces = "application/json")
    public  List<User> getFriendRequestFrom(@PathVariable String name) {
        int i = userRepository.findByName(name).getId();
        return userRepository.getFriendRequestFrom(i);
    }









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
}
