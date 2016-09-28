package com.hustunique.jianguo.dribile.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.Shots;

public class BaseActivity extends AppCompatActivity {
    protected static final String SHOT = "shot";

    protected String getTag() {
        return getClass().getSimpleName();
    }
    protected  void startActivity(Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startActivityWithShot(Class<? extends BaseActivity> clazz, Shots shot) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(SHOT, shot);
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
        String sb = shot.getTitle() + " - " +
                shot.getHtml_url() + "\n" +
                AppData.getString(R.string.share_from_dribile);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sb);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
