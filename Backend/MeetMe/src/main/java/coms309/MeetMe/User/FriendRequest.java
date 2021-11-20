
package coms309.MeetMe.User;

import javax.persistence.*;

import java.util.Date;


@Entity
class FriendRequest {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userA")
    private User userA;

    @ManyToOne
    @JoinColumn(name = "userB")
    private User userB;

    @Column(nullable = false)
    private Date created;

    @Enumerated(EnumType.STRING)
    private FriendRequestState state;


    // =============================== Constructors ================================== //


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