package coms309.MeetMe.Meeting;

import coms309.MeetMe.Location.Location;
import coms309.MeetMe.Stringy.Stringy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import coms309.MeetMe.User.Role;
import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping(value = "/", produces = "application/json")
    List<MeetingShadow> getAllMeeting() {
        return MeetingShadow.build(meetingRepository.findAll());
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    MeetingShadow getMeetingById( @PathVariable int id) {
        return new MeetingShadow(meetingRepository.findById(id));
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    MeetingShadow getUserByName(@PathVariable String name) {
        return new MeetingShadow(meetingRepository.findByName(name));
    }

    @GetMapping(value = "/search/{name}", produces = "application/json")
    List<MeetingShadow> getSearch(@PathVariable String name) {
        return MeetingShadow.build(meetingRepository.findBySearch(name));
    }

    @PostMapping(value = "/", produces = "application/json")
    String createMeeting(@RequestBody MeetingParams meetingParams) {

        // Check parameters exist
        if (!meetingParams.isValid())
            return Stringy.error("Invalid request body");

        // Keep meeting name unique
        Meeting checkIfExists = meetingRepository.findByName(meetingParams.name);
        if (checkIfExists != null) 
            return Stringy.error("Name already taken");

        // Find user associated with adminName
        User findAdmin = userRepository.findByName(meetingParams.adminName);

        if (findAdmin == null) 
            return Stringy.error("Admin not found");

        if (findAdmin.getRole() != Role.ADMIN)
            return Stringy.error("User is not an Admin");

        // Assemble location and meeting and save to database
        Meeting meeting = new Meeting(findAdmin, meetingParams.name, meetingParams.desc, meetingParams.dateTime, meetingParams.duration, meetingParams.loc);
        meetingRepository.save(meeting);
        return Stringy.success();
    }

    @DeleteMapping(value = "/id/{id}")
    String deleteMeeting(@PathVariable int id) {
        meetingRepository.deleteById(id);
        return Stringy.success();
    }

    @DeleteMapping(value = "/name/{name}")
    String deleteMeeting(@PathVariable String name) {
        Meeting meeting = meetingRepository.findByName(name);
        if (meeting == null)
            return Stringy.error("Name not found");

        meetingRepository.deleteById(meeting.getId());
        return Stringy.success();
    }

    @DeleteMapping(value = "/")
    String deleteMeeting() {
        meetingRepository.deleteAll();
        return Stringy.success();
    }
}


/**
 * Holds parameters that will be passed into POST meeting. 
 * @dateTime: 2007-12-03T10:15:30 format
 */
class MeetingParams {

    public String name, adminName, desc, dateTime;
    public Location loc;
    public int duration;
    
    public MeetingParams(String name, String adminName, String desc, String dateTime, int duration, String street, String city, String state, int zipcode, String country) {
        System.out.println("Meeting created: \n name:" + name + 
                            "\n adminName: " + adminName + 
                            "\n desc:" + desc + 
                            "\n desc: " + dateTime + 
                            "\n desc: " + duration + 
                            "\n loc: " + street + ", " + city + ", " + state + ", " + zipcode+ ", " + country);

        this.name = name;
        this.adminName = adminName;
        this.desc = desc;
        this.dateTime = dateTime;
        this.duration = duration;
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
