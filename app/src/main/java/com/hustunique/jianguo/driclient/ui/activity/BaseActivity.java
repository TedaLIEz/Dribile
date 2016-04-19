package com.hustunique.jianguo.driclient.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.Toast;

import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.bean.Shots;

public class BaseActivity extends AppCompatActivity {


    protected String getTag() {
        return getClass().getSimpleName();
    }
    protected  void startActivity(Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void showMessage(@NonNull final String msg) {
        if (TextUtils.isEmpty(msg)) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AppData.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startTwitterIntent(String username) {
        Intent intent = null;
        try {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + username));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + username));
        }
        startActivity(intent);
    }

    public void sendSharedIntent(Shots shot) {
        StringBuffer sb = new StringBuffer();
        sb.append(shot.getTitle()).append(" - ");
        sb.append(shot.getHtml_url()).append("\n");
        sb.append("- Shared from Driclient");
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
