package coms309.MeetMe.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    @Query(value = "SELECT * from friend_request WHERE userA.name = ?1 AND userA.name = ?2", nativeQuery = true)
    FriendRequest findByNames(String nameA, String nameB);

    @Query(value = "SELECT * from friend_request WHERE id=?1", nativeQuery = true)
    FriendRequest findById(int id);

    @Modifying
    @Transactional
    @Query(value = "delete from friend_request where id = ?1", nativeQuery = true)
    void deleteFriendRequest(int id);

    @Query(value = "select * from user inner join friend_re_quest on friend_re_quest.self_request_id = user.id where friend_re_quest.friend_request_id = ?1", nativeQuery = true)
    List<FriendRequest> getFriendRequestFrom(int f);
}
