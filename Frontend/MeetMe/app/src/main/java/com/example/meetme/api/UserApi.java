package com.example.meetme.api;

import com.example.meetme.model.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserApi {
    @GET("/JsonObject")
    Call<User> getUser();
}
