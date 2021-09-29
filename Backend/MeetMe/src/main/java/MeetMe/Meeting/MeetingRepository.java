package MeetMe.Meeting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Meeting findByName(String name);
    Meeting findByLocation(String add);
    List<Meeting> deleteByName(String name);
}


