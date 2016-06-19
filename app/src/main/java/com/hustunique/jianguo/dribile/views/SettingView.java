package com.hustunique.jianguo.dribile.views;

import android.content.Intent;

/**
 * Created by JianGuo on 5/26/16.
 */
public interface SettingView {
    void onClearSuccess();
    void onClearFailed(Exception e);
    void logout();

    void sendEmailIntent(Intent emailIntent);

    void about();

    void license();
}
