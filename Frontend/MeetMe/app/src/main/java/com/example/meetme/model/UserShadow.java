package com.example.meetme.model;

public class UserShadow {

    private String name;



    public UserShadow(int id, String name, String email, String joiningDate, String lastSeen, String role,
                      int[] meetingParticipation) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
