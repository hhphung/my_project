package com.example.meetme.api;

import com.example.meetme.model.Availability;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AvailabilityApi {
    /**
     * Sends the availability to the backend.
     * @param availability A class containing an arraylist of 168 boolean values and a username.
     * @return response indicating success or failure.
     */
    @Headers("Content-type: application/json")
    @POST("/availability/")
    Call<Availability> sendAvailability(@Body Availability availability);

    @Headers("Content-type: application/json")
    @GET("/availability/username/{name}")
    Call<Availability> getAvailability(@Path(value = "name")String username);
}
