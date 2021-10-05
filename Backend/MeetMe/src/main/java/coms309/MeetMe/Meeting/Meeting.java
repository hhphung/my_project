package coms309.MeetMe.Meeting;

import coms309.MeetMe.Location.Location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import coms309.MeetMe.User.User;


@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Location address;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private LocalTime time;

    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    // An admin user may host many meetings, but this meeting has one admin
    @ManyToOne
    @JoinColumn(nullable = false, unique = true)
    @JsonIgnore
    private User admin;

    // Users currently allowed in the meeting
    @ManyToMany
    // @JoinColumn(nullable = true)
    @JsonIgnore
    List<User> userParticipants;

    // Users who requested to join the meeting but have not been accepted yet
    @ManyToMany
    // @JoinColumn(nullable = true)
    @JsonIgnore
    List<User> userRequests;

    // Users who were invited to the meeting but have not accepted yet
    @ManyToMany
    // @JoinColumn(nullable = true)
    @JsonIgnore
    List<User> userInvites;


    // =============================== Constructors ================================== //

    public Meeting() {
        this.admin = new User();
        this.name = "anonymous";
        this.address = null;
        this.date = "Monday";
        this.time = null;
        this.privacy = Privacy.HIDDEN;
    }

    public Meeting(User admin) {
        this.admin = admin;
        this.name = "anonymous";
        this.address = null;
        this.date = "Monday";
        this.time = null;
        this.privacy = Privacy.HIDDEN;
    }

    public Meeting(User admin, String name, String desc, LocalTime time, Location loc) {
        this.admin = admin;
        this.name = name;
        this.address = loc;
        this.date = "Monday";
        this.time = time;
        this.privacy = Privacy.HIDDEN;
    }

    public Meeting(User admin, String name, Location address, String date, LocalTime time, Privacy privacy) {
        this.admin = admin;
        this.name = name;
        this.address = address;
        this.date = date;
        this.time = time;
        this.privacy = privacy;
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Location getAddress() {
        return address;
    }

    public void setAddress(Location address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}


