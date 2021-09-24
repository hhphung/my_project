package com.example.apicalls.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiClientFactory {

    static Retrofit apiClientSeed;

    static Retrofit GetApiClientSeed() {

        if(apiClientSeed == null) {
            apiClientSeed = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return apiClientSeed;
    }

    public static PostApi GetPostApi()
    {
        return GetApiClientSeed().create(PostApi.class);
    }
}
