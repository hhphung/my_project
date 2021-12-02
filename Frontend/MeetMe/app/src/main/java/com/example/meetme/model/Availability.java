package com.example.meetme.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Availability {
    /**
     * Username passed here to the backend.
     */
    @SerializedName("username")
    @Expose
    private String username;

    /**
     * Arraylist to be passed to the backend.
     */
    @SerializedName("hours")
    @Expose
    private ArrayList<Boolean> hours;

    public String getUsername() {
        return username;
    }

    public Availability(String username, Boolean[] hours){
        this.username = username;
        this.hours = new ArrayList<Boolean>();
        for (int i = 0; i < 168; i++){
            this.hours.add(hours[i]);
        }
    }

    public boolean isAvailableDuringRange(int start, int end){
        return false;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Boolean> getHours() {
        return this.hours;
    }

    public void setHours(Boolean[] hours) {
        this.hours = (ArrayList<Boolean>)Arrays.asList(hours) ;
    }
}
