package com.example.apicalls.api;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlimCallback<T> implements Callback<T> {

    LamdaInterface<T> lambdaInterface;

    String logTag;

    public SlimCallback(LamdaInterface<T> lambdaInterface)
    {
        this.lambdaInterface = lambdaInterface;
    }

    public SlimCallback(LamdaInterface<T> l, String CustomTag){
        this.lambdaInterface = l;
        this.logTag = CustomTag;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful())
        {
            lambdaInterface.doSomething(response.body());
        }
        else{
            Log.d(logTag, "Code: " + response.code() + "    Msg: " + response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d(logTag, "Thrown: " + t.getMessage());
    }
}
