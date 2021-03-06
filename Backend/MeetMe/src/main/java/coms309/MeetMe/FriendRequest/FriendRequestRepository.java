package coms309.MeetMe.FriendRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    @Query(value = "SELECT * from friend_request WHERE usera = ?1 AND userb = ?2", nativeQuery = true)
    FriendRequest findByUsers(int idA, int idB);

    @Query(value = "SELECT * from friend_request WHERE id=?1", nativeQuery = true)
    FriendRequest findById(int id);

    @Modifying
    @Transactional
    @Query(value = "delete from friend_request where id = ?1", nativeQuery = true)
    void deleteById(int id);

    @Query(value = "SELECT * FROM friend_request WHERE usera = ?1", nativeQuery = true)
    List<FriendRequest> findSent(int id);

    @Query(value = "SELECT * FROM friend_request WHERE userb = ?1", nativeQuery = true)
    List<FriendRequest> findReceived(int id);
}
