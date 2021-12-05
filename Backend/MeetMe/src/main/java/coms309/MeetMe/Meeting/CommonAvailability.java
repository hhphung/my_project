package coms309.MeetMe.Meeting;

import java.util.List;

public class CommonAvailability {


    private List<Integer> commonTime;
    private List<Integer> people;

    public CommonAvailability(List<Integer> commonTime, List<Integer> people) {
        this.commonTime = commonTime;
        this.people = people;
    }


    public List<Integer> getCommonTime() {
        return commonTime;
    }

    public void setCommonTime(List<Integer> commonTime) {
        this.commonTime = commonTime;
    }

    public List<Integer> getPeople() {
        return people;
    }

    public void setPeople(List<Integer> people) {
        this.people = people;
    }
}
