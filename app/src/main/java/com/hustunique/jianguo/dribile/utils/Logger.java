package com.hustunique.jianguo.dribile.utils;

import android.util.Log;

import com.hustunique.jianguo.dribile.BuildConfig;

/**
 * Created by JianGuo on 9/28/16.
 * Wrapper of {@link Log}
 */
public class Logger {
    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void wtf(String tag, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.wtf(tag, tr);
        }
    }
}
