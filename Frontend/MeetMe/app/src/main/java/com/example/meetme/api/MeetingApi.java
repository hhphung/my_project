package com.example.meetme.api;

import com.example.meetme.model.Meeting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MeetingApi {

    @GET("/meeting/{id}")
    Call<Meeting> getMeetingById(@Path(value = "id") String name);

    @POST("/meeting/")
    Call<Meeting> createMeeting(Meeting nMeeting);

    @GET("/meeting/")
    Call<List<Meeting>> getAllMeetings();
}
