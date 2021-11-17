package coms309.MeetMe.FriendShip;

import coms309.MeetMe.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendShipRepository extends JpaRepository<FriendShip, Integer> {

    @Query(value = "select * from user inner join friend_re_quest on friend_re_quest.self_request_id = user.id where friend_re_quest.friend_request_id = ?1", nativeQuery = true)
    List<User> getFriendRequestFrom(int f);


}
