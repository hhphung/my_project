package coms309.MeetMe.Availability;

import coms309.MeetMe.User.User;
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
public interface AvailabilityRepository extends JpaRepository<availability, Long> {
    availability findById(int id);


    @Query(value = "SELECT * from availability where availability.user_id=?1", nativeQuery = true)
    availability findUserbyId(int id);

    @Query(value = "SELECT availability from availability where availability.user_id=?1", nativeQuery = true)
    boolean[] getUserAvalibilityById(int id);


    @Modifying
    @Transactional
    @Query(value = "update availability set availability = ?1  where ?2", nativeQuery = true)
    void updateAvailability(boolean[] f ,String name);





}
