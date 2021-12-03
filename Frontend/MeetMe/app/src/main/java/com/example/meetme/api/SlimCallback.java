package com.example.meetme.api;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * To make calls easier to the server
 * @param <T>
 */
public class SlimCallback<T> implements Callback<T> {

    /**
     * An object with which to manipulate data.
     */
    LambdaInterface<T> lambdaInterface;

    /**
     * The message to display to logcat
     */
    String logTag;

    /**
     * Constructs a SlimCallback object with the param injected.
     * @param lambdaInterface
     */
    public SlimCallback(LambdaInterface<T> lambdaInterface)
    {
        this.lambdaInterface = lambdaInterface;
    }

    /**
     * Custom logtag constructor
     * @param l
     * @param CustomTag
     */
    public SlimCallback(LambdaInterface<T> l, String CustomTag){
        this.lambdaInterface = l;
        this.logTag = CustomTag;
    }

    /**
     * This is the function that is called when the server responds.
     * @param call The object the client sent to the server
     * @param response The object the server returns
     */
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful())
        {
            Log.d(logTag, "Successful Response: " + response.code() +   ", MSG: " + response.message() + ", Body: " + response.body());
            lambdaInterface.doSomething(response.body());
        }
        else{
            Log.d(logTag, "This is in SlimCallBack --> Code: " + response.code() + "    Body: " + response.body());
        }
    }

    /**
     * Log the failure for logcat
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d(logTag, "Thrown: " + t.getMessage());
    }
}

