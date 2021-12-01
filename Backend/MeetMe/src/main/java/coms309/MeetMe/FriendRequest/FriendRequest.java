
package coms309.MeetMe.FriendRequest;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coms309.MeetMe.User.*;

import java.util.Date;


@Entity
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userA")
    private User userA;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userB")
    private User userB;

    @Column(nullable = false)
    private Date created;

    @Enumerated(EnumType.STRING)
    private FriendRequestState state;


    // =============================== Constructors ================================== //
    
    public FriendRequest() {
        this.state = FriendRequestState.PENDING;
        this.created = new Date(System.currentTimeMillis());
    }

    public FriendRequest(User userA, User userB) {
        this.userA = userA;
        this.userB = userB;
        this.state = FriendRequestState.PENDING;
        this.created = new Date(System.currentTimeMillis());
    }

    public FriendRequest(User userA, User userB, FriendRequestState state) {
        this.userA = userA;
        this.userB = userB;
        this.state = state;
        this.created = new Date(System.currentTimeMillis());
    }


    // =============================== Getters and Setters for each field ================================== //


    public Integer getId() {
        return id;
    }

    public User getUserA() {
        return userA;
    }

    public User getUserB() {
        return userB;
    }

    public FriendRequestState getState() {
        return state;
    }

    public void accept() {
        state = FriendRequestState.ACCEPTED;
    }

    public void reject() {
        state = FriendRequestState.REJECTED;
    }

    public Date getCreated() {
        return created;
    }

    public void reset() {
        created = new Date(System.currentTimeMillis());
        state = FriendRequestState.PENDING;
    }
}
