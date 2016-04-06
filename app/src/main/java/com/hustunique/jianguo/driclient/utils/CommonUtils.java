package com.hustunique.jianguo.driclient.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by JianGuo on 4/4/16.
 */
public class CommonUtils {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean getNavigationBarVisibility(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Point realSize = getDisplayRealSize(context);

        int screen_height = Math.max(size.y, size.x);
        int real_screen_height = Math.max(realSize.y, realSize.x);
        return real_screen_height > screen_height;
    }

    public static Point getDisplayRealSize(Context context) {
        Point point = new Point();
        WindowManager wManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wManager.getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(point);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                point.x = (Integer) mGetRawW.invoke(display);
                point.y = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                display.getSize(point);
            }
        } else {
            display.getSize(point);
        }
        return point;
    }

    public static int getTransparentNavigationBarHeight(Context context) {
        // below version 4.4
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return 0;
        }
        // has navigation bar
        if (getNavigationBarVisibility(context)) {
            int resourceId = Resources.getSystem().getIdentifier("navigation_bar_height", "dimen",
                    "android");
            if (resourceId > 0) {
                return Resources.getSystem().getDimensionPixelSize(resourceId);
            }
        }
        return 0;
    }


    public static String formatDate(String time) {
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }
}
