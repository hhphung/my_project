package com.example.meetme.model;

public class Meeting {
    private String name;
    private String location;
    private String time;
    private String description;

    public Meeting(String name, String description, String location, String time)
    {
        this.location = location;
        this.description = description;
        this.name = name;
        this.time = time;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
