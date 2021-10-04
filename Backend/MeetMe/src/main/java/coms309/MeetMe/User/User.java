package coms309.MeetMe.User;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coms309.MeetMe.Meeting.Meeting;

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

    @ManyToMany(mappedBy = "userParticipants")
    // @JoinColumn(nullable = true)
    @JsonIgnore
    List<Meeting> meetingParticipation;

    @ManyToMany(mappedBy = "userRequests")
    // @JoinColumn(nullable = true)
    @JsonIgnore
    List<Meeting> meetingRequests;

    @ManyToMany(mappedBy = "userInvites")
    // @JoinColumn(nullable = true)
    @JsonIgnore
    List<Meeting> meetingInvites;

     // =============================== Constructors ================================== //


    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.joiningDate = new Date(System.currentTimeMillis());
        this.lastSeen = this.joiningDate;
        this.role = role;
    }

    public User() {
        this.name = "anonymous";
        this.password = "password";
        this.joiningDate = new Date(System.currentTimeMillis());
        this.lastSeen = this.joiningDate;
        this.role = Role.VIEWER;
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
