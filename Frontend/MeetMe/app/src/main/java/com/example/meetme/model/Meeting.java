package com.example.meetme.model;

import androidx.annotation.ArrayRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Meeting model to be sent in Api calls
 */
public class Meeting {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("adminName")
    @Expose
    private String adminName;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("duration")
    @Expose
    private int duration;


    @SerializedName("error")
    private String error;

    @SerializedName("message")
    @Expose
    private String responseMessage;


    //String name, String adminName, String desc, String dateTime, String street,
    // String city, String state, int zipcode, String country)

    /**
     * Constructor
     * @param name meeting name
     * @param adminName name of Admin creating meeting
     * @param desc meeting description
     * @param dateTime date & time: must be in correct format
     * @param location location of meeting
     */
    public Meeting(String name, String adminName, String desc, String dateTime, Location location)
    {
        this.name = name;
        this.adminName = adminName;
        this.desc = desc;
        this.dateTime = dateTime;
        this.location = location;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Location getLocation(){ return location; }

    public void setLocation(Location location){
        this.location = location;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }


    public int getDuration(){
        return duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
