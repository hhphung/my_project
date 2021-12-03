package com.example.meetme.model;

public class UserMeetingNamePair {
    private String username;
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
