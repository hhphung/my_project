package coms309.MeetMe.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Vivek Bengre
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT * from user WHERE id=?1", nativeQuery = true)
    User findById(int id);

    @Query(value = "SELECT * from user WHERE name=?1", nativeQuery = true)
    User findByName(String name);

    @Query(value = "delete*from friend_re_quest where id = ?1 and friend_id = ?2", nativeQuery = true)
    String deleteFriendRequest(int self, int f);

    @Query(value = "select id from friend_re_quest where friend_id =?1", nativeQuery = true)
    List<Integer> getFriendRequestFrom(int f);
}

