package com.hustunique.jianguo.dribile.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * AppData Helper
 */
public class AppData {
    private static Context sContext;
    public static final String EMAIL = "aliezted@gmail.com";
    public static final String HOME_PAGE = "http://tedaliez.github.io";
    public static final String TWITTER = "TedaLIEz";
    public static final String GITHUB = "TedaLIEz/dribile";
    public static void init(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        return sContext;
    }

    public static String getString(int id) {
        return sContext.getResources().getString(id);
    }

    public static String getString(int id, Object... args) {
        return sContext.getString(id, args);
    }

    public static int getColor(int resId) {
        return sContext.getResources().getColor(resId);
    }

    public static float getDimension(int resId) {
        return sContext.getResources().getDimension(resId);
    }

    public static Drawable getDrawable(int resId) {
        return sContext.getResources().getDrawable(resId);
    }

    public static Resources getResources() {
        return sContext.getResources();
    }

    public static void startActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sContext.startActivity(intent);
    }
}
