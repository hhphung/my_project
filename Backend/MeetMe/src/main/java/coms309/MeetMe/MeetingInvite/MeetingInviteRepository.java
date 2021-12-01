package coms309.MeetMe.MeetingInvite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface MeetingInviteRepository extends JpaRepository<MeetingInvite, Integer> {

    @Query(value = "SELECT * from meeting_invite WHERE user = ?1 AND meeting = ?2", nativeQuery = true)
    MeetingInvite findByUserMeeting(int idA, int idB);

    @Query(value = "SELECT * from meeting_invite WHERE id=?1", nativeQuery = true)
    MeetingInvite findById(int id);

    @Modifying
    @Transactional
    @Query(value = "delete from meeting_invite where id = ?1", nativeQuery = true)
    void deleteById(int id);

    @Query(value = "SELECT * FROM meeting_invite WHERE user = ?1", nativeQuery = true)
    List<MeetingInvite> findByUserId(int id);

    @Query(value = "SELECT * FROM meeting_invite WHERE meeting = ?1", nativeQuery = true)
    List<MeetingInvite> findByMeetingId(int id);
}
