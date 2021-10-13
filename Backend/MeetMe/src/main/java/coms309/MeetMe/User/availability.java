package coms309.MeetMe.User;

public class availability {
    private String day;
    private int from;
    private int to;

    public availability(String day, int from, int to) {
        this.day = day;
        this.from = from;
        this.to = to;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int setIndex(){
        int i = 0;

        if(day.equals("Tuesday")){
            i += 24;
        }
        else if(day.equals("Wednesday")){
            i+= 48;
        }
        else if(day.equals("Thursday")){
            i+= 72;
        }
        else if(day.equals("Friday")){
            i+= 96;
        }

        else if (day.equals("Saturday")){
            i+= 120;
        }
        else if(day.equals("Sunday")){
            i+= 144;
        }

        i += (to -from);

        return i;
    }
}
