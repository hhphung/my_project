package coms309.MeetMe.Availability;

public class Availability {
    private boolean[] hours;
    private String username;

    public Availability(boolean[] hours, String username) {
        this.hours = hours;
        this.username = username;
    }

    public boolean[] getHours() {
        return hours;
    }

    public void setHours(boolean[] hours) {
        this.hours = hours;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }
}

