package MeetMe.Meeting;

import MeetMe.Message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import coms309.MeetMe.Users.User;
import coms309.MeetMe.Location.Address;
import java.util.List;
import java.util.Optional;

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
