package com.example.apicalls.api;

import com.example.apicalls.model.Meeting;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MeetingApi {
    @GET("/meeting/")
    Call<Meeting> getAllMeetings();

    @GET("/meeting/{id}")
    Call<Meeting> getMeeting(@Path("id")int id);

    @POST("/meeting/")
    Call<Meeting> createMeeting(@Body Meeting nMeeting);
}
