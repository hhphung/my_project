package com.example.meetme.api;

import com.example.meetme.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @GET("/User/{id}")
    Call<User> getUser();

    @POST("/user/")
    Call<User> createUser(@Body User user);
}
