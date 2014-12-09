package com.umow.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Jonathan on 11/16/2014.
 */

public class ActivityJohn extends Activity_Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewResourceId() {
        return R.layout.activity_john;
    }

}