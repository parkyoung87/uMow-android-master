package com.umow.android.util;

import android.content.Context;
import android.widget.Toast;

import com.umow.android.ActivityUMow;

/**
 * Created by xuejianyu on 11/16/14.
 */

public class UtilToast {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
