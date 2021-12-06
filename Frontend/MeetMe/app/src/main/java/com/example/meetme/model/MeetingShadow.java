package com.example.meetme.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class MeetingShadow {

        private String name;

        private String admin;

        private String description;

        private Location location;

        private String dateTime;

        private int duration;

        private Privacy privacy;

        private List<String> userParticipants;


    public MeetingShadow(User admin, String name, String description, Location location, String dateTime, int duration, Privacy privacy, List<String> userParticipants){
        this.name = name;
        this.admin = admin.getName();
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.duration = duration;
        this.privacy = privacy;
        this.userParticipants = userParticipants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public List<String> getUserParticipants() {
        return userParticipants;
    }

    public void setUserParticipants(List<String> userParticipants) {
        this.userParticipants = userParticipants;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int[] getStartEndOfMeeting(){
        //2007-12-03T10:15:30
        try {
            LocalDateTime d = LocalDateTime.of(Integer.parseInt(dateTime.substring(0, 4)),
                    Integer.parseInt(dateTime.substring(5, 7)),
                    Integer.parseInt(dateTime.substring(8, 11)),
                    Integer.parseInt(dateTime.substring(12, 14)),
                    0);

            DayOfWeek mDay = d.getDayOfWeek();

            int day = mDay.getValue() - 1;

            int startTime = day * 24 + d.getHour();
            int endTime = startTime + duration;
            return new int[]{startTime, endTime};
        } catch (Exception e){
            return new int[]{-1,-1};
        }
    }
}
