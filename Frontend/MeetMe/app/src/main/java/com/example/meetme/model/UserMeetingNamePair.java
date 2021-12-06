package com.example.meetme.model;

import com.google.gson.annotations.SerializedName;

public class UserMeetingNamePair {

    @SerializedName("userName")
    private String username;
    @SerializedName("meetingName")
    private String meetingName;

    public UserMeetingNamePair(String username, String meetingName)
    {
        this.username = username;
        this.meetingName = meetingName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }
}
