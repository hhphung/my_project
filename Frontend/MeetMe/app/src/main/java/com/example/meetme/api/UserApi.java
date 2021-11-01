package com.example.meetme.api;

import com.example.meetme.model.User;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/user/{id}")
    Call<User> getUserById(@Path(value = "id") int id);

    @GET("/user/name/{username}")
    Call<User> getUserByName(@Path(value = "username") String username);

    @Headers("Content-type: application/json")
    @POST("/user/")
    Call<User> createUser(@Body User user);

    @GET("/user/")
    Call<List<User>> getAllUsers();
    @GET("/user/{name}/getFriends")
    Call<Set<User>> getFriends(@Path(value = "name") String name);

}
