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

    @GET("/meeting/{id}")
    Call<Meeting> getMeetingById(@Path(value = "id") int id);

    @GET("/meeting/{name}")
    Call<Meeting> getMeetingByName(@Path(value = "name") String name);

    @Headers("Content-type: application/json")
    @POST("/meeting/")
    Call<Meeting> createMeeting(@Body Meeting nMeeting);

    @GET("/meeting/")
    Call<List<Meeting>> getAllMeetings();

    @GET("/meeting/{name}")
    Call<List<Meeting>> getResults(@Path(value = "name") String name);
}
