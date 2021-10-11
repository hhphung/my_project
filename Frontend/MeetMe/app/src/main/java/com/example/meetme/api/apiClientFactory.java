package com.example.meetme.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiClientFactory {

    static Retrofit apiClientSeed;

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

    public static UserApi GetUserApi() { return GetApiClientSeed().create(UserApi.class); }

    public static MeetingApi GetMeetingApi() { return GetApiClientSeed().create(MeetingApi.class);}
}
