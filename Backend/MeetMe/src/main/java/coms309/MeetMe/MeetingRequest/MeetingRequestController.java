
package coms309.MeetMe.MeetingRequest;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Meeting.MeetingRepository;
import coms309.MeetMe.PushNotifications.model.Topic;
import coms309.MeetMe.PushNotifications.service.PushNotificationService;
import coms309.MeetMe.Stringy.Stringy;
import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/meetingRequest")
public class MeetingRequestController {

    @Autowired
    MeetingRequestRepository meetingRequestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MeetingRepository meetingRepository;

    private PushNotificationService pushNotificationService;

    public MeetingRequestController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    // get all
    @GetMapping(value = "/", produces = "application/json")
    List<MeetingRequest> getAllMeetingRequests() {
        return meetingRequestRepository.findAll();
    }


    // get by id
    @GetMapping(value = "/id/{id}", produces = "application/json")
    MeetingRequest getById(@PathVariable int id) {
        return meetingRequestRepository.findById(id);
    }
    

    // get by user id or name
    @GetMapping(value = "/userid/{id}", produces = "application/json")
    List<MeetingRequest> getByUserId(@PathVariable int id) {
        return meetingRequestRepository.findByUserId(id);
    }

    @GetMapping(value = "/username/{name}", produces = "application/json")
    List<MeetingRequest> getByUsername(@PathVariable String name) {
        User user = userRepository.findByName(name);
        if (user == null) return null;
        return meetingRequestRepository.findByUserId(user.getId());
    }

    // get by meeting id or name
    @GetMapping(value = "/meetingid/{id}", produces = "application/json")
    List<MeetingRequest> getByMeetingId(@PathVariable int id) {
        return meetingRequestRepository.findByMeetingId(id);
    }

    @GetMapping(value = "/meetingName/{name}", produces = "application/json")
    List<MeetingRequest> getByMeetingName(@PathVariable String name) {
        Meeting meeting = meetingRepository.findByName(name);
        if (meeting == null) return null;
        return meetingRequestRepository.findByMeetingId(meeting.getId());
    }

    // Add request
    @PostMapping(path ="/sendMeetingRequest", produces = "application/json")
    public String sendMeetingRequest(@RequestBody UserMeetingNamePair userMeetingNamePair) {

        if (userMeetingNamePair.isInvalid()) return Stringy.error("User or meeting was not found");

        User user = userRepository.findByName(userMeetingNamePair.getUserName());
        Meeting meeting = meetingRepository.findByName(userMeetingNamePair.getMeetingName());

        if (user == null || meeting == null) return Stringy.error("User or meeting was not found");

        if (user.getMeetingParticipation() != null && user.getMeetingParticipation().contains(meeting)) return Stringy.error("User already in meeting");
        
        MeetingRequest meetingRequest = meetingRequestRepository.findByUserMeeting(user.getId(), meeting.getId());

        // Meeting request already exists
        if (meetingRequest != null) {

            if (meetingRequest.getState() == MeetingRequestState.PENDING) return Stringy.error("Already sent");
            
            meetingRequest.reset();
        }
        else {

            meetingRequest = new MeetingRequest(user, meeting, MeetingRequestState.PENDING);
        }
        
        meetingRequestRepository.save(meetingRequest);
        
        // If user found, send out meeting request to meeting admin
        // pushNotificationService.sendPushNotificationToToken("title", "message", Topic.COMMON, "putTokenHere");
        
        // Currently this sends to every user, we need to save user tokens during registration.
        pushNotificationService.sendPushNotification("Meeting Request", user.getName() + " has requested to join" + meeting.getName() + "!", Topic.COMMON);
        
        return Stringy.success();
    }


    // ---- Accept ----


    // Accept request by username pair
    @PostMapping(path ="/acceptMeetingRequestNames", produces = "application/json")
    public String acceptMeetingRequestNames(@RequestBody UserMeetingNamePair userMeetingNamePair) {

        if (userMeetingNamePair.isInvalid()) return Stringy.error("User or meeting was not found");

        User user = userRepository.findByName(userMeetingNamePair.getUserName());
        Meeting meeting = meetingRepository.findByName(userMeetingNamePair.getMeetingName());

        if (user == null || meeting == null) return Stringy.error("User or meeting was not found");
        
        return acceptMeetingRequestCommon(meetingRequestRepository.findByUserMeeting(user.getId(), meeting.getId()));
    }

    // Accept request by id
    @PostMapping(path ="/acceptMeetingRequestId", produces = "application/json")
    public String acceptMeetingRequestId(@RequestBody int id) {
        return acceptMeetingRequestCommon(meetingRequestRepository.findById(id));
    }

    private String acceptMeetingRequestCommon(MeetingRequest meetingRequest) {
        if (meetingRequest == null) return Stringy.error("Meeting Request was not found");

        if (meetingRequest.getState() != MeetingRequestState.PENDING) return Stringy.error("Meeting request already finalized");

        // Save user and meeting
        meetingRequest.getUser().addMeeting(meetingRequest.getMeeting());
        meetingRequest.getMeeting().addUser(meetingRequest.getUser());
        userRepository.save(meetingRequest.getUser());
        meetingRepository.save(meetingRequest.getMeeting());

        // Save meeting in database
        meetingRequest.accept();
        meetingRequestRepository.save(meetingRequest);

        // Notify user
        pushNotificationService.sendPushNotification("Meeting Joined", "Your request to join meeting " + meetingRequest.getMeeting().getName() + " has been accepted!", Topic.COMMON);

        return Stringy.success();
    }


    // ---- Reject ----


    // Reject request by username pair
    @PostMapping(path ="/rejectMeetingRequestNames", produces = "application/json")
    public String rejectMeetingRequestNames(@RequestBody UserMeetingNamePair userMeetingNamePair) {

        if (userMeetingNamePair.isInvalid()) return Stringy.error("User or meeting was not found");

        User user = userRepository.findByName(userMeetingNamePair.getUserName());
        Meeting meeting = meetingRepository.findByName(userMeetingNamePair.getMeetingName());

        if (user == null || meeting == null) return Stringy.error("User or meeting was not found");
        
        return rejectMeetingRequestCommon(meetingRequestRepository.findByUserMeeting(user.getId(), meeting.getId()));
    }

    // Reject request by id
    @PostMapping(path ="/rejectMeetingRequestId", produces = "application/json")
    public String rejectMeetingRequestId(@RequestBody int id) {
        return rejectMeetingRequestCommon(meetingRequestRepository.findById(id));
    }

    private String rejectMeetingRequestCommon(MeetingRequest meetingRequest) {
        if (meetingRequest == null) return Stringy.error("Meeting Request was not found");

        if (meetingRequest.getState() != MeetingRequestState.PENDING) return Stringy.error("Meeting request already finalized");

        // Save meeting in database
        meetingRequest.reject();
        meetingRequestRepository.save(meetingRequest);

        // Notify user
        pushNotificationService.sendPushNotification("Rejected", "Your request to join meeting " + meetingRequest.getMeeting().getName() + " has been rejected!", Topic.COMMON);

        return Stringy.success();
    }
}
