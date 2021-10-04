package MeetMe.Meeting;

import MeetMe.Message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import coms309.MeetMe.Users.User;
import java.util.List;
import java.util.Optional;
import coms309.MeetMe.Users.UserRepository;
import coms309.MeetMe.Location.Address;
public class MeetingController {

    @Autowired
    MeetingRepository MeetingRepository;

    @Autowired
    MeetingService MeetingService;



    @GetMapping(path = "meeting/get/all")
    public @ResponseBody List<Meeting> getAllMeeting(){
        return MeetingService.getAllMeetings();
    }

    @GetMapping(path = "/meeting/get/{id}")
    Optional<Meeting> getUserById(@PathVariable long id){
        return MeetingRepository.findById(id);
    }


    @PutMapping(path  = "meeting/create")
    public @ResponseBody Message createMeeting(@RequestBody Meeting meet){
        return MeetingService.createMeeting(meet);
    }


    @PostMapping(path = "/meeting/post/{id}/{admin}")
    User findHost(@RequestBody Meeting meet){
        return MeetingService.findHost(meet);
    }

    @PostMapping(path = "/meeting/post/{id}/{address}")
    String findAddress(@RequestBody Meeting meet){
        return MeetingService.findAddress(meet);
    }



    @DeleteMapping(path = "delete/all")
    List<Meeting> deleteAllMeetings(){
        MeetingRepository.deleteAll();
        return MeetingRepository.findAll();
    }



}

