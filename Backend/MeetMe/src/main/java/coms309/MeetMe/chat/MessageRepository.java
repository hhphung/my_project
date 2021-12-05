
package coms309.MeetMe.chat;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>{

    @Query(value = "SELECT * from message WHERE meeting_id=?1", nativeQuery = true)
    List<Message> findByMeetingId (int i);

}

