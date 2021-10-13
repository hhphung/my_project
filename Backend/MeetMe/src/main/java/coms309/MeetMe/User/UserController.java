
package coms309.MeetMe.User;

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

    @GetMapping(value = "/id/{id}", produces = "application/json")
    User getUserById( @PathVariable int id) {
        return userRepository.findById(id);
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    User getUserByName( @PathVariable String name) {
        return userRepository.findByName(name);
    }

    @PostMapping(value = "/{name}/availability", produces = "application/json")
    User setAvailability(@PathVariable String name, @RequestBody List<availability> list){

        for(int i = 0; i < list.size(); i++){
            int index = list.get(i).setIndex();
            userRepository.findByName(name).getAvailability()[index] = true;
        }
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

    @PostMapping(path = "/user/login")
    String loginUser(@PathVariable String userName,@PathVariable String passWord ) {
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
