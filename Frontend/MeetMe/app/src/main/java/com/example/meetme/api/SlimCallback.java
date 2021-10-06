package com.example.meetme.api;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlimCallback<T> implements Callback<T> {

    LambdaInterface<T> lambdaInterface;

    String logTag;

    public SlimCallback(LambdaInterface<T> lambdaInterface)
    {
        this.lambdaInterface = lambdaInterface;
    }

    public SlimCallback(LambdaInterface<T> l, String CustomTag){
        this.lambdaInterface = l;
        this.logTag = CustomTag;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful())
        {
            Log.d(logTag, "Successful Response: " + response.code() +   ", MSG: " + response.message() + ", Body: " + response.body());
            lambdaInterface.doSomething(response.body());
        }
        else{
            Log.d(logTag, "This is in SlimCallBack --> Code: " + response.code() + "    Msg: " + response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d(logTag, "Thrown: " + t.getMessage());
    }
}

