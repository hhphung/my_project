package coms309.MeetMe.Meeting;

import coms309.MeetMe.Location.Location;
import coms309.MeetMe.Stringy.Stringy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import coms309.MeetMe.User.Role;
import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserRepository;

import java.util.List;
import java.time.LocalDateTime;

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
        if (!tempMeeting.isValid())
            return Stringy.error("Invalid request body");

        // Keep meeting name unique
        Meeting checkIfExists = meetingRepository.findByName(tempMeeting.name);
        if (checkIfExists != null) 
            return Stringy.error("Name already taken");

        // Find user associated with adminName
        User findAdmin = userRepository.findByName(tempMeeting.adminName);

        if (findAdmin == null) 
            return Stringy.error("Admin not found");

        if (findAdmin.getRole() != Role.ADMIN)
            return Stringy.error("User is not an Admin");

        // Assemble location and meeting and save to database
        Meeting meeting = new Meeting(findAdmin, tempMeeting.name, tempMeeting.desc, tempMeeting.dateTime, tempMeeting.loc);
        meetingRepository.save(meeting);
        return Stringy.success();
    }
}


/**
 * Holds parameters that will be passed into POST meeting. 
 * @dateTime: 2007-12-03T10:15:30 format
 */
class MeetingParams {

    public String name, adminName, desc;
    public LocalDateTime dateTime;
    public Location loc;

    MeetingParams(String name, String adminName) {
        
    }
    
    MeetingParams(String name, String adminName, String desc, String dateTime, String street, String city, String state, int zipcode, String country) {
        System.out.println("Meeting created: \n name:" + name + 
                            "\n adminName: " + adminName + 
                            "\n desc:" + desc + 
                            "\n desc: " + dateTime + 
                            "\n loc: " + street + ", " + city + ", " + state + ", " + zipcode+ ", " + country);

        this.name = name;
        this.adminName = adminName;
        this.desc = desc;
        this.dateTime = LocalDateTime.parse(dateTime);
        this.loc = new Location(street, city, state, zipcode, country);
    }

    public boolean isValid() {
        return  name != null && 
                adminName != null &&
                desc != null &&
                dateTime != null &&
                loc != null;
    }
}