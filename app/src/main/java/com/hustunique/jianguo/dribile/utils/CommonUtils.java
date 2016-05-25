package com.hustunique.jianguo.dribile.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.text.Html;
import android.text.Spanned;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.ui.activity.BaseActivity;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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


    /**
     * Format the created_at time to word date
     *
     * @param time the time string
     * @return the word date time, using in {@link com.hustunique.jianguo.dribile.ui.activity.ShotInfoActivity}
     */
    public static String formatDate(String time) {
        String[] times = time.split("T");
        String day = times[0];
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(day);
            DateFormat dateFormat1 = new SimpleDateFormat("MMMM dd, yyyy");
            return dateFormat1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String currentTimeLine(String date) {
        String[] times = date.split("T");
        String day = times[0];
        String time = times[1].substring(0, times[1].indexOf("Z"));
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date line = sdf.parse(day + " " + time);
            Date curr = c.getTime();
            long diff = curr.getTime() - line.getTime();
            int mins = (int) (diff / (1000 * 60));
            int hours = (int) (diff / (1000 * 60 * 60));
            if (mins < 60) {
                return mins + " mins";
            }
            if (hours < 24) {
                return hours + " hrs";
            } else {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date tmp = dateFormat.parse(day);
                return new SimpleDateFormat("MMMM dd, yyyy").format(tmp);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * return true if image ends with gif
     *
     * @param shot the image shots
     * @return <tt>true</tt> if image is gif format
     */
    public static boolean isGif(Shots shot) {
        return shot.getAnimated().equals("true");
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static Bitmap convertBitmap(Drawable drawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


    public static ColorMatrixColorFilter brightIt(int fb) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[]{
                1, 0, 0, 0, fb,
                0, 1, 0, 0, fb,
                0, 0, 1, 0, fb,
                0, 0, 0, 1, 0});

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(cmB);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);
        return f;
    }

    public static void hideSoftInputFromWindow(BaseActivity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static Spanned coloredString(@StringRes int stringId, @ColorRes int colorId, String string) {
        int color = AppData.getColor(colorId);
        String str = AppData.getString(stringId, color, string);
        return Html.fromHtml(str);
    }


}