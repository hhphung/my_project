package com.example.meetme.api;

import com.example.meetme.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/user/{id}")
    Call<User> getUserById(@Path(value = "id") int id);

    @GET("/user/{username}")
    Call<User> getUserByName(@Path(value = "username") String username);

    @POST("/user/")
    Call<User> createUser(@Body User user);

    @GET("/user/")
    Call<List<User>> getAllUsers();
}
