package coms309.MeetMe.User;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Stringy.Stringy;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
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
    private boolean[] availability = new boolean[168]; // TODO: Create Availability/Schedule object to pass in
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

        @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "friends",
            joinColumns = {@JoinColumn(name = "self_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id")})

    @JsonIgnore
    private Set<User> friends = new HashSet<User>();





    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "friendReQuest",
            joinColumns = {@JoinColumn(name = "self_request_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_request_id")}
    )
    private Set<User> friendReQuestSent = new HashSet<User>();






    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.email = "@";
        this.joiningDate = new Date(System.currentTimeMillis());
        this.lastSeen = this.joiningDate;
        this.role = role;

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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getJoiningDate() {
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

    public void addFriend(User friend) {
        friends.add(friend);
    }



    public void addFriendRequest(User friend) {
        friendReQuestSent.add(friend);
    }


    public Set<User> getFriendReQuest() {
        return friendReQuestSent;
    }





}

