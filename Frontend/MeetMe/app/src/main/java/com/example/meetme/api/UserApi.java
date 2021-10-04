package com.example.meetme.api;

import com.example.meetme.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/user/{id}")
    Call<User> getUser(@Path(value = "id") String username);

    @POST("/user/")
    Call<User> createUser(@Body User user);

    @GET("/user/")
    Call<User> getAllUsers();
}
