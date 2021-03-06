package com.example.meetme.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * User model to be sent in Api calls
 */
public class User {

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @SerializedName("message")
    private String response;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * Constructs a new user with the given username and password.
     * @param username
     * @param password
     */
    public User(String username, String password)
    {
        this.name = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
