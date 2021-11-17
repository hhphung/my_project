package coms309.MeetMe.Availability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vivek Bengre
 *
 */

@Repository
public interface AvailabilityRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);

    User findById(int id);

    @Query(value = "SELECT * from user WHERE name=?1", nativeQuery = true)
    User findByName(String name);

 
}
