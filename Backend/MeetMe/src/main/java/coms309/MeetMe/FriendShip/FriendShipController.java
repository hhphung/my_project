package coms309.MeetMe.FriendShip;


import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class FriendShipController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendShipRepository friendShipRepository;


    private String success = "{\"message\":\"Success\"}";
    private String failure = "{\"message\":\"User not found\"}";



    @PostMapping(value = "/AddFriendRequest", produces = "application/json")
    String addFriendRequets(@RequestBody FriendShip request) {
        if (request == null) {
            return failure;
        }
         friendShipRepository.save(request);
        return success;
    }


}
