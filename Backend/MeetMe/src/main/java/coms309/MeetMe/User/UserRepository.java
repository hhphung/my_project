<<<<<<< HEAD:Backend/MeetMe/src/main/java/MeetMe/Users/UserRepository.java
package coms309.MeetMe.Users;
import coms309.MeetMe.Users.User;
=======
package coms309.MeetMe.User;

>>>>>>> 75acccd7c929e221ce7262144de5e6c1d4cd4c71:Backend/MeetMe/src/main/java/coms309/MeetMe/User/UserRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vivek Bengre
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);

}
