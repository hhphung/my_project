package coms309.MeetMe.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import coms309.MeetMe.Users.User;
/**
 *
 * @author Vivek Bengre
 *
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);
    void deleteById(int id);
}
