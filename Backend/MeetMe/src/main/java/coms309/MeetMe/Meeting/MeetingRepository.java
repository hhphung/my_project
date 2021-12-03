package coms309.MeetMe.Meeting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Meeting findById(int id);

    @Query(value = "SELECT * from meeting WHERE name=?1", nativeQuery = true)
    Meeting findByName (String name);

    @Query(value = "SELECT * from meeting WHERE name LIKE %?1% OR description LIKE %?1%", nativeQuery = true)
    List<Meeting> findBySearch (String name);

    void deleteById(int id);
    void deleteAll();


}
