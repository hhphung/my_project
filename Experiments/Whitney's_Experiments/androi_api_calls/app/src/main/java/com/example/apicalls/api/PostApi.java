package com.example.apicalls.api;

import com.example.apicalls.model.Post;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostApi {

    @GET("posts/1")
    Call<Post> getFirstPost();
}
