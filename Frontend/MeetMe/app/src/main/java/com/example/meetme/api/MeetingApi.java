package com.example.meetme.api;

import com.example.meetme.model.Meeting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MeetingApi {

    /**
     * **THIS MIGHT BE DEPRECATED**
     * @param name meeting name to get
     * @return On success: A meeting object sent by the server. On failure:
     * A generic JSON response indicating a failure.
     */
    @GET("/meeting/{id}")
    Call<Meeting> getMeetingById(@Path(value = "id") String name);

    /**
     *
     * @param nMeeting A meeting object to post to the server.
     * @return a server response indicating success or failure.
     */
    @Headers("Content-type: application/json")
    @POST("/meeting/")
    Call<Meeting> createMeeting(@Body Meeting nMeeting);

    /**
     * @return A list of all meetings in the server.
     */
    @GET("/meeting/")
    Call<List<Meeting>> getAllMeetings();
}
