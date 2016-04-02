package com.hustunique.jianguo.driclient.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.hustunique.jianguo.driclient.app.AppData;

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
}
