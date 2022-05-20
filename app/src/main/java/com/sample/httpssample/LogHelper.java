package com.sample.httpssample;

import android.util.Log;

public class LogHelper {
    private static final String TAG = "log_debug";

    public static void d(String msg){
        Log.d(TAG,msg);
    }
}
