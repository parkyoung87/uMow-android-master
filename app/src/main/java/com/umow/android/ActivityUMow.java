package com.umow.android;

import android.content.Intent;
import android.os.Bundle;


public class ActivityUMow extends Activity_Base {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(ActivityUMow.this, ActivityFirstPage.class);
        startActivity(intent);
        finish();
    }

    @Override protected int getContentViewResourceId() {
        return R.layout.activity_umow;
    }
}
