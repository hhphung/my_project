package coms309.MeetMe.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vivek Bengre
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);

    User findById(int id);

    @Query(value = "SELECT * from user WHERE name=?1", nativeQuery = true)
    User findByName(String name);

    @Query(value = "delete*from friend_re_quest where id = self and friend_id = f", nativeQuery = true)
    String deleteFriendRequest(int self, int f);
}
