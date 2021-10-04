package coms309.MeetMe.Meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    MeetingRepository meetingRepository;


    @GetMapping(value = "/", produces = "application/json")
    List<Meeting> getAllMeetings(){
        return meetingRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    Optional<Meeting> getUserById(@PathVariable long id){
        return meetingRepository.findById(id);
    }

    @PostMapping(value = "/", produces = "application/json")
    Meeting createMeeing(@RequestBody Meeting meeting){
        meetingRepository.save(meeting);
        return meeting;
    }

    @DeleteMapping(value = "/{name}", produces = "application/json")
    List<Meeting> deleteByName(@RequestParam String name){
        meetingRepository.deleteByName(name);
        return meetingRepository.findAll();
    }

    // @DeleteMapping(value = "/all", produces = "application/json")
    // List<Meeting> deleteAllMeetings(){
    //     meetingRepository.deleteAll();
    //     return meetingRepository.findAll();
    // }


}

