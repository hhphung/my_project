package coms309.MeetMe.Meeting;

import coms309.MeetMe.Location.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import coms309.MeetMe.User.Role;
import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserRepository;

import java.util.List;
import java.time.LocalTime;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping(value = "/", produces = "application/json")
    List<Meeting> getAllMeeting() {
        return meetingRepository.findAll();
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    Meeting getMeetingById( @PathVariable int id) {
        return meetingRepository.findById(id);
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    Meeting getUserByName(@PathVariable String name) {
        return meetingRepository.findByName(name);
    }

    @PostMapping(value = "/", produces = "application/json")
    String createMeeting(@RequestBody MeetingParams tempMeeting) {

        // Check parameters exist
        if (tempMeeting.name == null || tempMeeting.desc == null || tempMeeting.time == null || tempMeeting.loc == null)
            return "{\"message\":\"Invalid request body\"}";

        // Keep meeting name unique
        Meeting checkIfExists = meetingRepository.findByName(tempMeeting.name);
        if (checkIfExists != null) 
            return "{\"message\":\"Name taken\"}";

        // Find user associated with adminName
        User findAdmin = userRepository.findByName(tempMeeting.adminName);

        if (findAdmin == null) 
            return "{\"message\":\"Admin not found\"}";

        if (findAdmin.getRole() != Role.ADMIN)
            return "{\"message\":\"User is not an Admin\"}";

        // Assemble location and meeting and save to database
        Location location = new Location(tempMeeting.loc);
        Meeting meeting = new Meeting(findAdmin, tempMeeting.name, tempMeeting.desc, LocalTime.now(), location);
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


/**
 * Holds parameters that will be passed into POST meeting. 
 */
class MeetingParams {

    public String name, adminName, desc, loc, time;

    MeetingParams(String name, String adminName, String desc, String time, String loc) {
        System.out.println(name + adminName + desc + time + loc);
        this.name = name;
        this.adminName = adminName;
        this.desc = desc;
        this.time = time;
        this.loc = loc;
    }
}