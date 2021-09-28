package com.example.meetme.model;

import com.google.gson.annotations.SerializedName;

public class User {
    //Just for using the current Postman API
    @SerializedName("name")
    private String username;
    @SerializedName("Number")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
