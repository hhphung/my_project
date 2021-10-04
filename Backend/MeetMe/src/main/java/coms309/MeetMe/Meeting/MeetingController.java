package coms309.MeetMe.Meeting;

import coms309.MeetMe.Message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import coms309.MeetMe.User.User;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
     MeetingRepository meetingRepository;


    @GetMapping(value = "/get/all", produces = "application/json")
    public @ResponseBody List<Meeting> getAllMeeting() {
        return meetingRepository.findAll();
    }

    // @GetMapping(value = "/id/{id}", produces = "application/json")
    // Meeting getMeetingById( @PathVariable int id) {
    //     return meetingRepository.findById(id);
    // }

    // @GetMapping(value = "/name/{name}", produces = "application/json")
    // Optional<Meeting> getUserByName(@PathVariable String name) {
    //     return meetingRepository.findByName(name);
    // }

    @PostMapping(value = "/", produces = "application/json")
    String createMeeting(@RequestBody Meeting meeting) {
        if (meeting == null)
            return "{\"message\":\"Invalid request body\"}";

        Meeting checkIfExists = meetingRepository.findByName(meeting.getName());

        if (checkIfExists != null) 
            return "{\"message\":\"Username taken\"}";

            meetingRepository.save(meeting);
        return "{\"message\":\"Success!\"}";
    }


    // @PutMapping(path  = "meeting/create")
    // public @ResponseBody Message createMeeting(@RequestBody Meeting meet){
    //     return MeetingService.createMeeting(meet);
    // }


    // @PostMapping(path = "/meeting/{id}/admin")
    // public User findHost(@RequestBody Meeting meet){
    //     return MeetingService.findHost(meet);
    // }

    // @PostMapping(path = "/meeting/{id}/address")
    // public String findAddress(@RequestBody Meeting meet){
    //     return MeetingService.findAddress(meet);
    // }
}
