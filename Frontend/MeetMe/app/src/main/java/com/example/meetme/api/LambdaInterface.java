package com.example.meetme.api;

/**
 * interface used by SlimCallback class
 * @param <T>
 */
public interface LambdaInterface<T> {

    /**
     * Does something
     * @param result
     * do something with this
     */
    public void doSomething(T result);
}
