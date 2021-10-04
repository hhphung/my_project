package MeetMe.Meeting;
import   coms309.MeetMe.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import coms309.MeetMe.Location.Address;
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Meeting findMeetingbyName (String name);
    Meeting findMeetingbyLocation (String name);
    User findHost (Meeting meet);
    String findAddress(Meeting Meet);
    


}


