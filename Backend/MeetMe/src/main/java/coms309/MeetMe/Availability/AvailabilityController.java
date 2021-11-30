
package coms309.MeetMe.Availability;

import coms309.MeetMe.Stringy.Stringy;
import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    AvailabilityRepository availabilityRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping(value = "/", produces = "application/json")
    public List<boolean[]> getAllAvalibility() {
        List<Availability> avail = availabilityRepository.findAll();
        List<boolean[]> reducedAva = new ArrayList<boolean[]>();
        
        avail.forEach(ava -> {
            reducedAva.add(ava.getAvailability());
        });

        return reducedAva;
    }


    @GetMapping(value = "/id/{id}", produces = "application/json")
    public boolean[] getAvailabilityByID (@PathVariable int id) {
        return availabilityRepository.findById(id);
    }


    @GetMapping(value = "/userid/{id}", produces = "application/json")
    public boolean[] getAvalibilityByUserID(@PathVariable int id) {
        return availabilityRepository.findByUserID(id);
    }


    @GetMapping(value = "/username/{name}", produces = "application/json")
    public boolean[] getUserAvailibilityName(@PathVariable String name){
        return availabilityRepository.findByUsername(name);
    }


    @PostMapping(value = "/setAvailability", produces = "application/json")
    public String setAvailability(@RequestBody AvailabilityInput input) {
        
        if (input == null) return Stringy.error("Availability is invalid");
    
        User user = userRepository.findByName(input.getName());

        if (user == null) return Stringy.error("User not found");
        
        if (availabilityRepository.findByUserID(user.getId()) != null) {
            availabilityRepository.updateAvailability(input.getHours(), user.getId());
            return Stringy.success();
        }

        Availability ava = new Availability(input.getHours(), user);

        availabilityRepository.save(ava);

        return Stringy.success();
    }
}
