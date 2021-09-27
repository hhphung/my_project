package MeetMe.Meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import coms309.MeetMe.Users.User;
import java.util.List;
import java.util.Optional;
import coms309.MeetMe.Users.UserRepository;
public class MeetingController {

    @Autowired
    MeetingRepository MeetingRepository;

    @Autowired
    UserRepository UserRepository;


    @GetMapping(path = "meeting/get/all")
    List<Meeting> getAllMeetings(){
        return MeetingRepository.findAll();
    }

    @GetMapping(path = "/meeting/get/{id}")
    Optional<Meeting> getUserById(@PathVariable long id){

        return MeetingRepository.findById(id);
    }


    

    @PostMapping(path = "/meeting/post/newmeet")
    Meeting createMeeing(@RequestBody Meeting meet){
        MeetingRepository.save(meet);
        return meet;
    }


    @DeleteMapping(path = "delete/all")
    List<Meeting> deleteAllMeetings(){
        MeetingRepository.deleteAll();
        return MeetingRepository.findAll();
    }


}

