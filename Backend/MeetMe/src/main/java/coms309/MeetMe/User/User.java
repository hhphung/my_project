package coms309.MeetMe.User;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.Stringy.Stringy;
import coms309.MeetMe.FriendRequest.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

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
    private Set<Meeting> meetingAdmin;

    @ManyToMany(mappedBy = "userParticipants")
    @JsonIgnore
    private Set<Meeting> meetingParticipation;

    @ManyToMany(mappedBy = "userRequests")
    @JsonIgnore
    private Set<Meeting> meetingRequests;

    @ManyToMany(mappedBy = "userInvites")
    @JsonIgnore
    private Set<Meeting> meetingInvites;


    // Friends are a many-to-many user-to-user relationship
    // It's about to get complicated
    @JsonManagedReference
    @ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    @JoinTable(name="friendsA",
            joinColumns={@JoinColumn(name="friendsB_id")},
            inverseJoinColumns={@JoinColumn(name="friendsA_id")})
    @JsonIgnore
    private Set<User> friendsA = new HashSet<User>();

    @JsonBackReference
    @ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    @JoinTable(name="friendsB",
            joinColumns={@JoinColumn(name="friendsA_id")},
            inverseJoinColumns={@JoinColumn(name="friendsB_id")})
    @JsonIgnore
    private Set<User> friendsB = new HashSet<User>();

    
    @OneToMany(mappedBy = "userA")
    @JsonIgnore
    private Set<FriendRequest> friendRequestsSent;

    @OneToMany(mappedBy = "userB")
    @JsonIgnore
    private Set<FriendRequest> friendRequestsReceived;


    // =============================== Constructors ================================== //


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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Combine friend relationship
    public Set<User> getFriends() {
        Set<User> friends = new HashSet<User>();
        friends.addAll(friendsA);
        friends.addAll(friendsB);
        return friends;
    }

    public void addFriend(FriendRequest friendRequest) {
        if (name.equals(friendRequest.getUserA().getName())) {  // User is sending
            friendsA.add(friendRequest.getUserB());
        }
        else {  // User is receiving
            friendsB.add(friendRequest.getUserA());
        }
    }

    public void removeFriend(User friend) {
        friendsA.remove(friend);
        friendsB.remove(friend);
    }

    // Sent
    public Set<FriendRequest> getFriendRequestsSent() {
        return friendRequestsSent;
    }

    public void sendFriendRequest(FriendRequest friendRequest) {
        friendRequestsSent.add(friendRequest);
    }

    public boolean removeFriendRequestSent(FriendRequest friendRequestSent) {
        return friendRequestsSent.remove(friendRequestSent);
    }

    // Received
    public Set<FriendRequest> getFriendRequestsReceived() {
        return friendRequestsReceived;
    }

    public void receiveFriendRequest(FriendRequest friendRequest) {
        friendRequestsReceived.add(friendRequest);
    }

    public boolean removeFriendRequestReceived(FriendRequest friendRequestReceived) {
        return friendRequestsReceived.remove(friendRequestReceived);
    }
}
