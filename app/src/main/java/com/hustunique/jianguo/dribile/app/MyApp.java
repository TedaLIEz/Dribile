package com.hustunique.jianguo.dribile.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.ui.activity.LoginActivity;
import com.hustunique.jianguo.dribile.ui.activity.MainActivity;

/**
 * Created by JianGuo on 3/29/16.
 * Custom Application
 */
public class MyApp extends Application {

    public static final String client_id = "1a0491ebe3baf11a4075942e5a4c8b7f9e3a45d5f6ebdc04efe09a9f6488663e";
    public static final String client_secret = "c347124356e372468d2a757ef0cfdf1c977627a22bd0d352610755fc6bf34618";
    public static final String redirect_url = "http://127.0.0.1";


    @Override
    public void onCreate() {
        super.onCreate();
        AppData.init(getApplicationContext());
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (activity instanceof LoginActivity) {
                    if (MyAccountManager.hasAccount()) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
