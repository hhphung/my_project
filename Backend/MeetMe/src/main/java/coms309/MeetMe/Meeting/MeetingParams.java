package coms309.MeetMe.Meeting;

import coms309.MeetMe.Location.Location;


/**
 * Holds parameters that will be passed into POST meeting. 
 * @dateTime: 2007-12-03T10:15:30 format
 */
class MeetingParams {

    public String name, adminName, desc, dateTime;
    public Location loc;
    public int duration;

    public MeetingParams(String name, String adminName, String desc, String dateTime, int duration, Location location) {
        System.out.println("Meeting created: \n name:" + name + 
                            "\n adminName: " + adminName + 
                            "\n desc:" + desc + 
                            "\n desc: " + dateTime + 
                            "\n desc: " + duration + 
                            "\n loc: " + location.toString());

        this.name = name;
        this.adminName = adminName;
        this.desc = desc;
        this.dateTime = dateTime;
        this.duration = duration;
        this.loc = location;
    }

    public boolean isValid() {
        return  name != null && 
                adminName != null &&
                desc != null &&
                dateTime != null &&
                loc != null;
    }
}