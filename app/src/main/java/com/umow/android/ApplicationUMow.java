package com.umow.android;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by xuejianyu on 11/19/14.
 */
public class ApplicationUMow extends Application {

    private final String PARSE_APPLICATION_ID = "XRTzd1rnrqrA6p7dqpO2tAXaX4pdshsqXNneqsFV";
    private final String PARSE_CLIENT_KEY = "gAn0uo7u4UULObOtZ6WVJjzk2wAX4bl31qI6QCRB";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
    }
}
