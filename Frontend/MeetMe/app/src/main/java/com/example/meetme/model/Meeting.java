package com.example.meetme.model;

public class Meeting {
    private String name;
    private String loc;
    private String time;
    private String desc;
    private String adminName;

    public Meeting(String name, String adminName, String desc, String time, String loc)
    {
        this.loc = loc;
        this.desc = desc;
        this.name = name;
        this.time = time;
        this.adminName = adminName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
