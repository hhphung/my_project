
package coms309.MeetMe.MeetingInvite;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Meeting.MeetingRepository;
import coms309.MeetMe.MeetingRequest.UserMeetingNamePair;
import coms309.MeetMe.PushNotifications.service.PushNotificationService;
import coms309.MeetMe.Stringy.Stringy;
import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/meetingInvite")
public class MeetingInviteController {

    @Autowired
    MeetingInviteRepository meetingInviteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MeetingRepository meetingRepository;

    private PushNotificationService pushNotificationService;

    public MeetingInviteController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    // get all
    @GetMapping(value = "/", produces = "application/json")
    List<MeetingInvite> getAllMeetingInvites() {
        return meetingInviteRepository.findAll();
    }


    // get by id
    @GetMapping(value = "/id/{id}", produces = "application/json")
    MeetingInvite getById(@PathVariable int id) {
        return meetingInviteRepository.findById(id);
    }

    // get by user id or name
    @GetMapping(value = "/userid/{id}", produces = "application/json")
    List<MeetingInvite> getByUserId(@PathVariable int id) {
        return meetingInviteRepository.findByUserId(id);
    }

    @GetMapping(value = "/username/{name}", produces = "application/json")
    public List<MeetingInvite> getByUsername(@PathVariable String name) {
        User user = userRepository.findByName(name);
        if (user == null) return null;
        return meetingInviteRepository.findByUserId(user.getId());
    }

    // get by meeting id or name
    @GetMapping(value = "/meetingid/{id}", produces = "application/json")
    List<MeetingInvite> getByMeetingId(@PathVariable int id) {
        return meetingInviteRepository.findByMeetingId(id);
    }

    @GetMapping(value = "/meetingName/{name}", produces = "application/json")
    List<MeetingInvite> getByMeetingName(@PathVariable String name) {
        Meeting meeting = meetingRepository.findByName(name);
        if (meeting == null) return null;
        return meetingInviteRepository.findByMeetingId(meeting.getId());
    }

    // Add request
    @PostMapping(path ="/sendMeetingInvite", produces = "application/json")
    public String sendMeetingInvite(@RequestBody UserMeetingNamePair userMeetingNamePair) {

        if (userMeetingNamePair.isInvalid()) return Stringy.error("User or meeting was not found");

        User user = userRepository.findByName(userMeetingNamePair.getUserName());
        Meeting meeting = meetingRepository.findByName(userMeetingNamePair.getMeetingName());

        if (user == null || meeting == null) return Stringy.error("User or meeting was not found");

        if (user.getMeetingParticipation() != null && user.getMeetingParticipation().contains(meeting.getId())) return Stringy.error("User already in meeting");
        
        MeetingInvite meetingRequest = meetingInviteRepository.findByUserMeeting(user.getId(), meeting.getId());

        // Meeting request already exists
        if (meetingRequest != null) {

            if (meetingRequest.getState() == MeetingInviteState.PENDING) return Stringy.error("Already sent");
            
            meetingRequest.reset();
        }
        else {

            meetingRequest = new MeetingInvite(user, meeting, MeetingInviteState.PENDING);
        }
        
        meetingInviteRepository.save(meetingRequest);
        
        pushNotificationService.sendPushNotification("Meeting invite", 
            "You have been invited to join" + meeting.getName() + "!", 
            user.getName());
        
        return Stringy.success();
    }


    // ---- Accept ----


    // Accept request by username pair
    @PostMapping(path ="/acceptMeetingInviteNames", produces = "application/json")
    public String acceptMeetingInviteNames(@RequestBody UserMeetingNamePair userMeetingNamePair) {

        if (userMeetingNamePair.isInvalid()) return Stringy.error("User or meeting was not found");

        User user = userRepository.findByName(userMeetingNamePair.getUserName());
        Meeting meeting = meetingRepository.findByName(userMeetingNamePair.getMeetingName());

        if (user == null || meeting == null) return Stringy.error("User or meeting was not found");
        
        return acceptMeetingInviteCommon(meetingInviteRepository.findByUserMeeting(user.getId(), meeting.getId()));
    }

    // Accept request by id
    @PostMapping(path ="/acceptMeetingInviteId", produces = "application/json")
    public String acceptMeetingInviteId(@RequestBody int id) {
        return acceptMeetingInviteCommon(meetingInviteRepository.findById(id));
    }

    private String acceptMeetingInviteCommon(MeetingInvite meetingRequest) {
        if (meetingRequest == null) return Stringy.error("Meeting Request was not found");

        if (meetingRequest.getState() != MeetingInviteState.PENDING) return Stringy.error("Meeting request already finalized");

        // Save user and meeting
        meetingRequest.getUser().addMeeting(meetingRequest.getMeeting());
        meetingRequest.getMeeting().addUser(meetingRequest.getUser());
        userRepository.save(meetingRequest.getUser());
        meetingRepository.save(meetingRequest.getMeeting());

        // Save meeting in database
        meetingRequest.accept();
        meetingInviteRepository.save(meetingRequest);

        // Notify admin
        pushNotificationService.sendPushNotification("User accepted", 
            meetingRequest.getUser().getName() + " has accepted your invitation to join" + meetingRequest.getMeeting().getName(), 
            meetingRequest.getMeeting().getAdmin().getName());

        return Stringy.success();
    }


    // ---- Reject ----


    // Reject request by username pair
    @PostMapping(path ="/rejectMeetingInviteNames", produces = "application/json")
    public String rejectMeetingInviteNames(@RequestBody UserMeetingNamePair userMeetingNamePair) {

        if (userMeetingNamePair.isInvalid()) return Stringy.error("User or meeting was not found");

        User user = userRepository.findByName(userMeetingNamePair.getUserName());
        Meeting meeting = meetingRepository.findByName(userMeetingNamePair.getMeetingName());

        if (user == null || meeting == null) return Stringy.error("User or meeting was not found");
        
        return rejectMeetingInviteCommon(meetingInviteRepository.findByUserMeeting(user.getId(), meeting.getId()));
    }

    // Reject request by id
    @PostMapping(path ="/rejectMeetingInviteId", produces = "application/json")
    public String rejectMeetingInviteId(@RequestBody int id) {
        return rejectMeetingInviteCommon(meetingInviteRepository.findById(id));
    }

    private String rejectMeetingInviteCommon(MeetingInvite meetingRequest) {
        if (meetingRequest == null) return Stringy.error("Meeting Request was not found");

        if (meetingRequest.getState() != MeetingInviteState.PENDING) return Stringy.error("Meeting request already finalized");

        // Save meeting in database
        meetingRequest.reject();
        meetingInviteRepository.save(meetingRequest);

        // Notify admin
        pushNotificationService.sendPushNotification("User rejected", 
            meetingRequest.getUser().getName() + " has rejected your invitation to join" + meetingRequest.getMeeting().getName(), 
            meetingRequest.getMeeting().getAdmin().getName());

        return Stringy.success();
    }
}


