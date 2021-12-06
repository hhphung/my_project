
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
    public List<AvailabilityInput> getAllAvalibility() {

        List<Availability> avail = availabilityRepository.findAll();
        
        List<AvailabilityInput> reducedAva = new ArrayList<AvailabilityInput>();
        
        avail.forEach(ava -> {
            reducedAva.add(new AvailabilityInput(ava));
        });

        return reducedAva;
    }


    @GetMapping(value = "/id/{id}", produces = "application/json")
    public AvailabilityInput getAvailabilityByID (@PathVariable int id) {
        Availability avail = availabilityRepository.findById(id);
        if (avail == null) return null;
        return new AvailabilityInput(avail);
    }


    @GetMapping(value = "/userid/{id}", produces = "application/json")
    public AvailabilityInput getAvalibilityByUserID(@PathVariable int id) {
        Availability avail = availabilityRepository.findByUserID(id);
        if (avail == null) return null;
        return new AvailabilityInput(avail);
    }


    @GetMapping(value = "/username/{name}", produces = "application/json")
    public AvailabilityInput getUserAvailibilityName(@PathVariable String name) {
        User user = userRepository.findByName(name);
        if (user == null) return null;
        Availability avail = availabilityRepository.findByUserID(user.getId());
        if (avail == null) return null;
        return new AvailabilityInput(avail);
    }


    @PostMapping(value = "/", produces = "application/json")
    public String setAvailability(@RequestBody AvailabilityInput input) {
        
        if (input == null || input.getHours() == null || input.getName() == null) return Stringy.error("Availability is invalid");
    
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
