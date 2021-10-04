package com.example.meetme.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiClientFactory {

    static Retrofit apiClientSeed;

    static Retrofit GetApiClientSeed() {

        if (apiClientSeed == null) {
            apiClientSeed = new Retrofit.Builder()
                    .baseUrl("http://coms-309-017.cs.iastate.edu")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return apiClientSeed;
    }

    public static UserApi GetUserApi() { return GetApiClientSeed().create(UserApi.class); }

}
