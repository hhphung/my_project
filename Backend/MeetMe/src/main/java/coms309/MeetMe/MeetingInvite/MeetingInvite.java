package coms309.MeetMe.MeetingInvite;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coms309.MeetMe.Meeting.Meeting;
import coms309.MeetMe.User.*;

import java.util.Date;


@Entity
public class MeetingInvite {

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

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String meetName;

    @Enumerated(EnumType.STRING)
    private MeetingInviteState state;


    // =============================== Constructors ================================== //


    public MeetingInvite() {
        this.state = MeetingInviteState.PENDING;
        this.created = new Date(System.currentTimeMillis());
    }

    public MeetingInvite(User user, Meeting meeting) {
        this.user = user;
        this.meeting = meeting;
        this.state = MeetingInviteState.PENDING;
        this.created = new Date(System.currentTimeMillis());
        this.userName = user.getName();
        this.meetName = meeting.getName();
    }

    public MeetingInvite(User user, Meeting meeting, MeetingInviteState state) {
        this.user = user;
        this.meeting = meeting;
        this.state = state;
        this.created = new Date(System.currentTimeMillis());
        this.userName = user.getName();
        this.meetName = meeting.getName();
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

    public MeetingInviteState getState() {
        return state;
    }

    public void accept() {
        state = MeetingInviteState.ACCEPTED;
    }

    public void reject() {
        state = MeetingInviteState.REJECTED;
    }

    public Date getCreated() {
        return created;
    }

    public void reset() {
        created = new Date(System.currentTimeMillis());
        state = MeetingInviteState.PENDING;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMeetName() {
        return meetName;
    }

    public void setMeetName(String meetName) {
        this.meetName = meetName;
    }
}
