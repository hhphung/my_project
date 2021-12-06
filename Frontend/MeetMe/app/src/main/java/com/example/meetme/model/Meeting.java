package com.example.meetme.model;

import android.os.Build;

import androidx.annotation.ArrayRes;
import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zipcode")
    @Expose
    private int zipcode;
    @SerializedName("country")
    @Expose()
    private String country;

    @SerializedName("error")
    private String error;
    @SerializedName("message")
    @Expose
    private String responseMessage;


    @SerializedName("duration")
    @Expose
    private int durationHours;

    //String name, String adminName, String desc, String dateTime, String street,
    // String city, String state, int zipcode, String country)

    /**
     * Constructor
     * @param name meeting name
     * @param adminName name of Admin creating meeting
     * @param desc meeting description
     * @param dateTime date & time: must be in correct format
     * @param street street address of meeting location
     * @param city city of meeting location
     * @param state state of meeting location
     * @param zipcode zip of meeting location
     * @param country country of meeting location
     */
    public Meeting(String name, String adminName, String desc, String dateTime, String street,
                   String city, String state, int zipcode, String country, int durationHours)
    {
        this.name = name;
        this.adminName = adminName;
        this.desc = desc;
        this.dateTime = dateTime;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
        this.durationHours = durationHours;

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
            int endTime = startTime + durationHours;
            return new int[]{startTime, endTime};
        } catch (Exception e){
            return new int[]{-1,-1};
        }
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ArrayList<String> getParticipants() {
        return new ArrayList<String>();
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
