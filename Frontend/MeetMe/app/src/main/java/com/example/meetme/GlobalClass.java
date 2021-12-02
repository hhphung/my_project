package com.example.meetme;

import android.app.Application;

public class GlobalClass extends Application {

    private String username;

    private String meetingName;

    public String getName() {
        return username;
    }

    public void setName(String aName) {
        username = aName;
    }

    public String getMeetingName(){
        return meetingName;
    }

    public void setMeetingName(String newName){
        meetingName = newName;
    }
}
