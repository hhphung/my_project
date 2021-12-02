package com.example.meetme;

import android.app.Application;

public class GlobalClass extends Application {

    private String username;


    public String getName() {

        return username;
    }

    public void setName(String aName) {

        username = aName;

    }
}
