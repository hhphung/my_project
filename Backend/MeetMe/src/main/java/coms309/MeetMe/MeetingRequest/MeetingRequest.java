package coms309.MeetMe.MeetingRequest;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.User.*;

import java.util.Date;


@Entity
public class MeetingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "meeting")
    private Meeting meeting;

    @Column(nullable = false)
    private Date created;

    @Enumerated(EnumType.STRING)
    private MeetingRequestState state;


    // =============================== Constructors ================================== //
    
    public MeetingRequest() {
        this.state = MeetingRequestState.PENDING;
        this.created = new Date(System.currentTimeMillis());
    }

    public MeetingRequest(User user, Meeting meeting) {
        this.user = user;
        this.meeting = meeting;
        this.state = MeetingRequestState.PENDING;
        this.created = new Date(System.currentTimeMillis());
    }

    public MeetingRequest(User user, Meeting meeting, MeetingRequestState state) {
        this.user = user;
        this.meeting = meeting;
        this.state = state;
        this.created = new Date(System.currentTimeMillis());
    }


    // =========================== Getters and Setters for each field ============================== //


    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public String getUserName() {
        return user.getName();
    }

    public String getMeetingName() {
        return meeting.getName();
    }

    public MeetingRequestState getState() {
        return state;
    }

    public void accept() {
        state = MeetingRequestState.ACCEPTED;
    }

    public void reject() {
        state = MeetingRequestState.REJECTED;
    }

    public Date getCreated() {
        return created;
    }

    public void reset() {
        created = new Date(System.currentTimeMillis());
        state = MeetingRequestState.PENDING;
    }
}
