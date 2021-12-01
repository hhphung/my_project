
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
        System.out.println(avail.size());
        avail.forEach(ava -> {
            System.out.println(ava.getHours() + " - " + ava.getUser().getName());
            reducedAva.add(ava.getHours());
        });

        return reducedAva;
    }


    @GetMapping(value = "/id/{id}", produces = "application/json")
    public boolean[] getAvailabilityByID (@PathVariable int id) {
        Availability avail = availabilityRepository.findById(id);
        if (avail == null) return null;
        return avail.getHours();
    }


    @GetMapping(value = "/userid/{id}", produces = "application/json")
    public boolean[] getAvalibilityByUserID(@PathVariable int id) {
        Availability avail = availabilityRepository.findByUserID(id);
        if (avail == null) return null;
        return avail.getHours();
    }


    @GetMapping(value = "/username/{name}", produces = "application/json")
    public boolean[] getUserAvailibilityName(@PathVariable String name) {
        User user = userRepository.findByName(name);
        if (user == null) return null;
        Availability avail = availabilityRepository.findByUserID(user.getId());
        if (avail == null) return null;
        return avail.getHours();
    }


    @PostMapping(value = "/", produces = "application/json")
    public String setAvailability(@RequestBody AvailabilityInput input) {
        
        if (input == null || input.isInvalid()) return Stringy.error("Availability is invalid");
    
        User user = userRepository.findByName(input.getName());

        if (user == null) return Stringy.error("User not found");

        Availability avail = availabilityRepository.checkByUserID(user.getId());

        if (avail != null) {
            availabilityRepository.updateAvailability(input.getHours(), user.getId());
            return Stringy.success();
        }

        avail = new Availability(input.getHours(), user);

        availabilityRepository.save(avail);

        return Stringy.success();
    }
}
