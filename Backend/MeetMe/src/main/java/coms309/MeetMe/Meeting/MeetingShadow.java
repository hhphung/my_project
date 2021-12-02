package coms309.MeetMe.Meeting;

import coms309.MeetMe.Location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MeetingShadow {

    private int id;

    private String name;

    private String admin;

    private String description;

    private Location location;

    private String dateTime;

    private int duration;

    private Privacy privacy;

    private List<String> userParticipants;


    // =============================== Constructors ================================== //

    public MeetingShadow() {

    }

    public MeetingShadow(Meeting meeting) {
        this.admin = meeting.getAdmin().getName();
        this.name = meeting.getName();
        this.description = meeting.getDescription();
        this.location = meeting.getLocation();
        this.dateTime = meeting.getDateTime();
        this.duration = meeting.getDuration();
        this.privacy = meeting.getPrivacy();
        this.userParticipants = meeting.getUserParticipants();
    }

    public static List<MeetingShadow> build(List<Meeting> meetings) {
        List<MeetingShadow> rebuild = new ArrayList<MeetingShadow>();
        meetings.forEach(meeting -> {
            rebuild.add(new MeetingShadow(meeting));
        });
        return rebuild;
    }

    public static List<MeetingShadow> build(Set<Meeting> meetings) {
        List<MeetingShadow> rebuild = new ArrayList<MeetingShadow>();
        meetings.forEach(meeting -> {
            rebuild.add(new MeetingShadow(meeting));
        });
        return rebuild;
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAdmin() {
        return admin;
    }

    public Location getLocation() {
        return location;
    }


    public String getDateTime() {
        return dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getUserParticipants() {
        return userParticipants;
    }
}
