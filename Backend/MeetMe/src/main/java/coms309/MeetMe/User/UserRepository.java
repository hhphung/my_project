package coms309.MeetMe.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 *
 * @author Vivek Bengre
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT * from user WHERE id=?1", nativeQuery = true)
    User findById(int id);

    @Query(value = "SELECT * from user WHERE name=?1", nativeQuery = true)
    User findByName(String name);


    @Modifying
    @Transactional
    @Query(value = "delete from friend_re_quest where self_request_id = ?1 and ?2", nativeQuery = true)
    void deleteFriendRequest(int self, int f );


    @Query(value = "select * from user inner join friend_re_quest on friend_re_quest.self_request_id = user.id where friend_re_quest.friend_request_id = ?1", nativeQuery = true)
    List<User> getFriendRequestFrom(int f);

    @Query(value = "SELECT * from user WHERE name LIKE %?1%", nativeQuery = true)
    List<User> findBySearch (String name);



}

