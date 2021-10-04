<<<<<<< HEAD:Backend/MeetMe/src/main/java/MeetMe/Users/UserController.java
package coms309.MeetMe.Users;
;
import coms309.MeetMe.Users.User;
import coms309.MeetMe.Users.UserRepository;
=======
package coms309.MeetMe.User;

>>>>>>> 75acccd7c929e221ce7262144de5e6c1d4cd4c71:Backend/MeetMe/src/main/java/coms309/MeetMe/User/UserController.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

<<<<<<< HEAD:Backend/MeetMe/src/main/java/MeetMe/Users/UserController.java
    @GetMapping(path = "/users/{id}")
    Optional<User> getUserById( @PathVariable long id){
=======
    @GetMapping(value = "/{id}", produces = "application/json")
    User getUserById( @PathVariable int id){
>>>>>>> 75acccd7c929e221ce7262144de5e6c1d4cd4c71:Backend/MeetMe/src/main/java/coms309/MeetMe/User/UserController.java
        return userRepository.findById(id);
    }

    @PostMapping(value = "/", produces = "application/json")
    String createUser(@RequestBody User user){
        if (user == null)
            return failure;
        userRepository.save(user);
        return success;
    }

    @PostMapping(path = "/user/login")
    String loginUser(@PathVariable String userName,@PathVariable String passWord ){
        userRepository.findAll();
        return success;
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
