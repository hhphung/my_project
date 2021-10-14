package coms309.MeetMe.User;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Stringy.Stringy;

import java.util.Date;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Date joiningDate;
    @Column(nullable = false)
    private Date lastSeen;
    @Column(nullable = false)
    private String availability; // TODO: Create Availability/Schedule object to pass in
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    List<Meeting> meetingAdmin;

    @ManyToMany(mappedBy = "userParticipants")
    @JsonIgnore
    List<Meeting> meetingParticipation;

    @ManyToMany(mappedBy = "userRequests")
    @JsonIgnore
    List<Meeting> meetingRequests;

    @ManyToMany(mappedBy = "userInvites")
    @JsonIgnore
    List<Meeting> meetingInvites;

     // =============================== Constructors ================================== //


    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.email = "@";
        this.joiningDate = new Date(System.currentTimeMillis());
        this.lastSeen = this.joiningDate;
        this.role = role;
        this.availability = "Never free";
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.email = "@";
        this.joiningDate = new Date(System.currentTimeMillis());
        this.lastSeen = this.joiningDate;
        this.role = Role.VIEWER;
        this.availability = "Never free";
    }

    public User() {
        this.name = Stringy.getRandom(10);
        this.password = "password";
        this.email = "@";
        this.joiningDate = new Date(System.currentTimeMillis());
        this.lastSeen = this.joiningDate;
        this.role = Role.VIEWER;
        this.availability = "Never free";
    }


    
    // =============================== Getters and Setters for each field ================================== //


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getJoiningDate(){
        return joiningDate;
    }

    public String getAvailability() { return availability; }

    public void setAvailability(String availability) { this.availability = availability; }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
