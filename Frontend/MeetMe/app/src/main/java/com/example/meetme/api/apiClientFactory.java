package com.example.meetme.api;

import com.example.meetme.model.Availability;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class used for communication between frontend and server
 */
public class apiClientFactory {
    /**
     *  This is the retrofit object. It takes the base URL hard-coded in and uses it to
     *  make HTTP Requests.
     */
    static Retrofit apiClientSeed;

    /**
     * Builds a new retrofit object iff there is not one already.
     * @return apiClientSeed: The retrofit object the client uses to communicate with the server
     */
    static Retrofit GetApiClientSeed() {

        if (apiClientSeed == null) {
            apiClientSeed = new Retrofit.Builder()
                    //.baseUrl("https://90f5d506-b47d-45fe-a176-b7061ac29f55.mock.pstmn.io")
                    .baseUrl("http://coms-309-017.cs.iastate.edu:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return apiClientSeed;
    }

    /**
     *
     * @return A retrofit implementation of the UserApi
     */
    public static UserApi GetUserApi() { return GetApiClientSeed().create(UserApi.class); }

    /**
     *
     * @return A retrofit implementation of the MeetingApi
     */
    public static MeetingApi GetMeetingApi() { return GetApiClientSeed().create(MeetingApi.class);}
    public static AvailabilityApi GetAvailabilityApi() {return GetApiClientSeed().create(AvailabilityApi.class);}
}
