package com.umow.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.umow.android.util.UtilLogger;

/**
 * Created by xuejianyu on 11/16/14.
 */

public abstract class Activity_Base extends Activity {

    protected final String TAG = getClass().getSimpleName();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilLogger.log(TAG, "onCreate()");

        setContentView(getContentViewResourceId());
    }

    protected abstract int getContentViewResourceId();

}
