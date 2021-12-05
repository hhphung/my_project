package coms309.MeetMe.Meeting;

import coms309.MeetMe.Location.Location;
import coms309.MeetMe.MeetingRequest.MeetingRequest;
import coms309.MeetMe.MeetingInvite.MeetingInvite;
import coms309.MeetMe.Stringy.Stringy;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

import coms309.MeetMe.User.User;
import coms309.MeetMe.User.UserShadow;

import lombok.Data;


@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)   // null == undecided
    private Location location;

    @Column(nullable = true)   // null == undecided
    private String dateTime;

    @Column(nullable = true)   // null == undecided
    private int duration;

    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    // An admin user may host many meetings, but this meeting has one admin
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User admin;

    // Users currently allowed in the meeting
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // @JoinColumn(nullable = true)
    @JsonIgnore
    Set<User> userParticipants;

    // Users who requested to join the meeting but have not been accepted yet
    @OneToMany
    @JsonIgnore
    Set<MeetingRequest> meetingRequests;

    // Users who were invited to the meeting but have not accepted yet
    @OneToMany
    @JsonIgnore
    Set<MeetingInvite> meetingInvites;

    // // Users who requested to join the meeting but have not been accepted yet
    // @ManyToMany
    // // @JoinColumn(nullable = true)
    // @JsonIgnore
    // List<User> userRequests;

    // Users who were invited to the meeting but have not accepted yet
    // @ManyToMany
    // // @JoinColumn(nullable = true)
    // @JsonIgnore
    // List<User> userInvites;


    // =============================== Constructors ================================== //

    // Should only be used for testing purposes
    // Creates a new random user to be admin for this meeting
    public Meeting() {
        this.admin = new User();
        this.name = Stringy.getRandom(10);
        this.privacy = Privacy.HIDDEN;
    }

    public Meeting(User admin) {
        this.admin = admin;
        this.name = Stringy.getRandom(10);
        this.privacy = Privacy.HIDDEN;
    }

    public Meeting(User admin, String name, String desc, String dateTime, int duration, Location location) {
        this.admin = admin;
        this.name = name;
        this.description = desc;
        this.location = location;
        this.dateTime = dateTime;
        this.duration = duration;
        this.privacy = Privacy.HIDDEN;
    }

    public Meeting(User admin, String name, String desc, Location location, String dateTime, int duration, Privacy privacy) {
        this.admin = admin;
        this.name = name;
        this.description = desc;
        this.location = location;
        this.dateTime = dateTime;
        this.duration = duration;
        this.privacy = privacy;
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
        List<String> ids = new ArrayList<String>();
        userParticipants.forEach(user -> {
            ids.add(user.getName());
        });
        return ids;
    }

    public void addUser(User user) {
        this.userParticipants.add(user);
    }
}
