package coms309.MeetMe.Availability;

public class AvailabilityInput {
    private boolean[] hours;
    private String username;

    public AvailabilityInput(boolean[] hours, String username) {
        this.hours = hours;
        this.username = username;
    }

    public AvailabilityInput(Availability avail) {
        this.hours = avail.getHours();
        this.username = avail.getUserName();
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

