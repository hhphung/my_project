package coms309.MeetMe.Meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Meeting findByName(String name);
    // Meeting findByAddress(String address);
    List<Meeting> deleteByName(String name);
}


