package coms309.MeetMe.MeetingRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface MeetingRequestRepository extends JpaRepository<MeetingRequest, Integer> {

    @Query(value = "SELECT * from meeting_request WHERE user = ?1 AND meeting = ?2", nativeQuery = true)
    MeetingRequest findByUserMeeting(int idA, int idB);

    @Query(value = "SELECT * from meeting_request WHERE id=?1", nativeQuery = true)
    MeetingRequest findById(int id);

    @Modifying
    @Transactional
    @Query(value = "delete from meeting_request where id = ?1", nativeQuery = true)
    void deleteById(int id);

    @Query(value = "SELECT * FROM meeting_request WHERE user = ?1", nativeQuery = true)
    List<MeetingRequest> findByUserId(int id);

    @Query(value = "SELECT * FROM meeting_request WHERE meeting = ?1", nativeQuery = true)
    List<MeetingRequest> findByMeetingId(int id);
}
