package coms309.MeetMe.Meeting;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import coms309.MeetMe.User.User;
import coms309.MeetMe.Meeting.MeetingRepository;
import java.util.List;

public class MeetingService {
    @Autowired
    MeetingRepository MeetingRepository;

   public Meeting findMeetingbyName(String name){
       return MeetingRepository.findMeetingbyName(name);
   }

    public List<Meeting> getAllMeetings(){
        return MeetingRepository.findAll();
    }

    public User findHost (Meeting meet){
       return MeetingRepository.findHost(meet);
    }
    public String findAddress(Meeting meet){
       return meet.getAddress().getAddress();
    }
    public Message createMeeting(Meeting meet){
       MeetingRepository.save(meet);
       return new Message("Create Successful");
    }

}
