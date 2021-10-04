package coms309.MeetMe.Meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import coms309.MeetMe.User.User;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Meeting findMeetingbyName (String name);
    Meeting findMeetingbyLocation (String name);
    User findHost (Meeting meet);
    String findAddress(Meeting Meet);


}


