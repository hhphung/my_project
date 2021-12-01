package coms309.MeetMe.Availability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 *
 * @author Vivek Bengre
 *
 */

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    
    
    @Query(value = "SELECT * from availability WHERE id=?1", nativeQuery = true)
    Availability findById(int id);

    @Query(value = "SELECT * FROM availability WHERE availability.user_id=?1", nativeQuery = true)
    Availability findByUserID(int id);

    @Query(value = "SELECT * FROM availability WHERE availability.user_id=?1", nativeQuery = true)
    Availability checkByUserID(int id);

    // @Query(value = "SELECT availability from availability where availability.user_id=?1", nativeQuery = true)
    // boolean[] getUserAvalibilityById(int id);

    @Modifying
    @Transactional
    @Query(value = "update availability set availability.hours = ?1 where availability.user_id= ?2", nativeQuery = true)
    void updateAvailability(boolean[] f ,int id);

}
