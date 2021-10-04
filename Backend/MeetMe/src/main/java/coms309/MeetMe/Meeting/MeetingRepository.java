package coms309.MeetMe.Meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Meeting findById(int id);

    @Query(value = "SELECT * from meeting WHERE name=?1", nativeQuery = true)
    Meeting findByName (String name);

    // Meeting findMeetingbyLocation (String name);
    // User findHost (Meeting meet);
    // String findAddress(Meeting Meet);


}


