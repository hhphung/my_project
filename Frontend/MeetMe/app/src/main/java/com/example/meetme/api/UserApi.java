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

/**
 * All mapping for grabbing user data from the server
 */
public interface UserApi {
    /**
     * Returns a user with the given id.
     * @param id id of user to get from the server
     * @return A user of given id
     */
    @GET("/user/{id}")
    Call<User> getUserById(@Path(value = "id") int id);

    /**
     * Returns a user with the given name.
     * @param username name of user to get from the server
     * @return A user of given name
     */
    @GET("/user/name/{username}")
    Call<User> getUserByName(@Path(value = "username") String username);

    /**
     * Send a new user to the server.
     * @param user User object to send to the server
     * @return response indicating success or failure.
     */
    @Headers("Content-type: application/json")
    @POST("/user/")
    Call<User> createUser(@Body User user);

    /**
     *
     * @return a list of all users
     */
    @GET("/user/")
    Call<List<User>> getAllUsers();

    /**
     * Checks if a user can login
     * @param user
     * @return "success" or "failure"
     */
    @POST("/user/login/")
    Call<User> canLogin(@Body User user);

    /**
     * Calls the server for a list of users that the user {name} is friends with.
     * @param name name of user to get friends of
     * @return a list of friends of the user given by name
     */
    @GET("/user/{name}/getFriends")
    Call<Set<User>> getFriends(@Path(value = "name") String name);


}
