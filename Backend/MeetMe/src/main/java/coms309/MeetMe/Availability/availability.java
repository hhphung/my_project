package coms309.MeetMe.Availability;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Stringy.Stringy;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;

    @Column(nullable = false, unique = true)
    private Integer userID;

    @Column(nullable = false)
    private boolean [] availability = new boolean[168]; // TODO: Create Availability/Schedule object to pass in

     // =============================== Constructors ================================== //

    public Availability(Integer userID, String password, Role role) {
        this.userID = userID;
        
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.email = "@";
        this.joiningDate = new Date(System.currentTimeMillis());
        this.lastSeen = this.joiningDate;
        this.role = Role.VIEWER;

    }

    public User() {
        this.name = Stringy.getRandom(10);
        this.password = "password";
        this.email = "@";
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

    public boolean[] getAvailability() {
        return availability;
    }

    public void setAvailability(boolean[] availability) {
        this.availability = availability;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }



    public Set<User> getFriends() {
        return friends;
    }
    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }
    public void addFriend(User friend){
        friends.add(friend);
    }



    public Set<User> getFriendRequests() {
        return friendRequest;
    }

    public boolean removeFriendRequest(User friend) {
        return friendRequest.remove(friend);
    }

    public Set<User> getRequestFrom(){
        return requestFrom;
    }
}
