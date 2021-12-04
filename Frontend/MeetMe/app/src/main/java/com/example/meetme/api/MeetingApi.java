package com.example.meetme.api;

import com.example.meetme.model.Meeting;
import com.example.meetme.model.MeetingShadow;
import com.example.meetme.model.UserMeetingNamePair;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * All mapping for grabbing meeting data from the server
 */
public interface MeetingApi {

    /**
     * **THIS MIGHT BE DEPRECATED**
     * @param id user id
     * @return On success: A meeting object sent by the server. On failure:
     * A generic JSON response indicating a failure.
     */
    @GET("/meeting/{id}")
    Call<MeetingShadow> getMeetingById(@Path(value = "id") int id);

    @GET("/meeting/name/{name}")
    Call<MeetingShadow> getMeetingByName(@Path(value = "name") String name);

    @POST("/meetingInvite/sendMeetingInvite")
    Call<String>  sendInvite(@Body UserMeetingNamePair pair);

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
    Call<List<MeetingShadow>> getAllMeetings();

    @GET("/meeting/search/{name}")
    Call<List<MeetingShadow>> getResults(@Path(value = "name") String name);
}
