package com.example.meetme.model;

public class UserNamePair {
    private String userNameA, userNameB;

    private String message;
    private String reason;
    public UserNamePair(String userNameA, String userNameB) {
        this.userNameA = userNameA;
        this.userNameB = userNameB;
    }

    public String getUserNameA() { return userNameA; }

    public String getUserNameB() { return userNameB; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setUserNameA(String userNameA) { this.userNameA = userNameA; }

    public void setUserNameB(String userNameB) { this.userNameB = userNameB; }

    public boolean isInvalid() { return (userNameA == null || userNameB == null || userNameA.equals(userNameB)); }
}
