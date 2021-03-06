package coms309.MeetMe.Meeting;

import coms309.MeetMe.Availability.Availability;
import coms309.MeetMe.Availability.AvailabilityRepository;
import coms309.MeetMe.Stringy.Stringy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import coms309.MeetMe.User.Role;
import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AvailabilityRepository avaRepository;

    @GetMapping(value = "/", produces = "application/json")
    List<MeetingShadow> getAllMeeting() {
        return MeetingShadow.build(meetingRepository.findAll());
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    MeetingShadow getMeetingById( @PathVariable int id) {
        return new MeetingShadow(meetingRepository.findById(id));
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    public MeetingShadow getUserByName(@PathVariable String name) {
        return new MeetingShadow(meetingRepository.findByName(name));
    }

    @GetMapping(value = "/search/{name}", produces = "application/json")
    List<MeetingShadow> getSearch(@PathVariable String name) {
        return MeetingShadow.build(meetingRepository.findBySearch(name));
    }



    @PostMapping(value = "/", produces = "application/json")
    String createMeeting(@RequestBody MeetingParams meetingParams) {

        // Check parameters exist
        if (!meetingParams.isValid())
            return Stringy.error("Invalid request body");

        // Keep meeting name unique
        Meeting checkIfExists = meetingRepository.findByName(meetingParams.name);
        if (checkIfExists != null) 
            return Stringy.error("Name already taken");

        // Find user associated with adminName
        User findAdmin = userRepository.findByName(meetingParams.adminName);

        if (findAdmin == null) 
            return Stringy.error("Admin not found");

        if (findAdmin.getRole() != Role.ADMIN)
            return Stringy.error("User is not an Admin");

        // Assemble location and meeting and save to database
        Meeting meeting = new Meeting(findAdmin, meetingParams.name, meetingParams.desc, meetingParams.dateTime, meetingParams.duration, meetingParams.loc);
        findAdmin.addMeeting(meeting);
        userRepository.save(findAdmin);
        meetingRepository.save(meeting);
        return Stringy.success();
    }



    @DeleteMapping(value = "/id/{id}")
    String deleteMeeting(@PathVariable int id) {
        meetingRepository.deleteById(id);
        return Stringy.success();
    }

    @DeleteMapping(value = "/name/{name}")
    String deleteMeeting(@PathVariable String name) {
        Meeting meeting = meetingRepository.findByName(name);
        if (meeting == null)
            return Stringy.error("Name not found");

        meetingRepository.deleteById(meeting.getId());
        return Stringy.success();
    }

    @DeleteMapping(value = "/")
    String deleteMeeting() {
        meetingRepository.deleteAll();
        return Stringy.success();
    }
/*
    @GetMapping(value = "/commonAvailability/")
       public List<Integer>  getCommonAvailability(){
        List<Integer> list = new ArrayList<Integer>();
        List<Availability>  avaList = avaRepository.findAll();
        boolean temp = false;
        Availability first = avaList.get(0);
        if(first != null) {
            for (int i = 0; i < first.getHours().length; i++) {
                if (first.getHours()[i] == true){
                    temp = true;
                    for(Availability x : avaList){
                        if(x.getHours()[i] == false){
                            temp = false;
                            break;
                        }
                    }
                    if(temp == true){
                        list.add(i);
                    }
                }
            }
        }
        return list;
    }

*/

    @GetMapping(value = "/commonAvailability/")
  public CommonAvailability getCommonAvailability(){

        int [] list = new int [168];
        List<Availability>  avaList = avaRepository.findAll();
        boolean temp = false;
       for(Availability x : avaList){

           for(int i = 0; i < x.getHours().length; i ++){
               if(x.getHours()[i] == true){
                   list[i] += 1;
               }
           }
       }
        List<Integer> result = new ArrayList<Integer>();

        for(int i = 0 ; i < list.length; i ++){
            if(list[i] != 0){
                result.add(i);
            }

       }


        // One by one move boundary of unsorted subarray
            for (int i = 0; i < result.size() - 1; i++)
        {
            int max = result.get(i);
            // Find the minimum element in unsorted array
             int index = i;
             int value = result.get(i);
            for (int j = i+1; j < result.size(); j++) {
                if (list[result.get(j)] > list[max]) {
                    max = result.get(j);
                    index = j;
                }
            }
            // Swap the found minimum element with the first
            // element

            result.set(i, max);
            result.set(index, value);

        }

            List<Integer> people = new ArrayList<Integer>();
            for(int i = 0 ; i < result.size(); i ++){
                people.add(list[result.get(i)]);
            }

            CommonAvailability c = new CommonAvailability(result, people);

        return c;
    }




}
