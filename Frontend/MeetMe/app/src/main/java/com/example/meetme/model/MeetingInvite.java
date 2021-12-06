package com.example.meetme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class MeetingInvite {

    @SerializedName("id")
    @Expose
    private Integer id;


    @SerializedName("created")
    @Expose
    private Date created;

    @SerializedName("meetName")
    @Expose
    private String meeting;

    @SerializedName("userName")
    @Expose
    private String user;


    @SerializedName("state")
    @Expose
    private MeetingInviteState state;


    // =============================== Constructors ================================== //


    public MeetingInvite() {
        this.state = MeetingInviteState.PENDING;
        this.created = new Date(System.currentTimeMillis());
    }


    // =========================== Getters and Setters for each field ============================== //


    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getMeeting() {
        return meeting;
    }

    public void setMeeting(String meeting) {
        this.meeting = meeting;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

