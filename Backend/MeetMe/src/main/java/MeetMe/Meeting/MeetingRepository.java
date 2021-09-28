package MeetMe.Meeting;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Meeting findByName(String name);
    Meeting findByLocation(String add);
}


