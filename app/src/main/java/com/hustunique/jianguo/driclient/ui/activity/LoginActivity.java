package com.hustunique.jianguo.driclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.bean.AccessToken;
import com.hustunique.jianguo.driclient.bean.OAuthUser;
import com.hustunique.jianguo.driclient.bean.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Login Activity for application
 */
public class LoginActivity extends BaseActivity {
    private static final int LOGIN = 0x00000000;
    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;

    private boolean animationStarted = false;
    @Bind(R.id.btn_login)
    Button login;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void animate() {
        ImageView logoImageView = (ImageView) findViewById(R.id.img_logo);
        ViewGroup container = (ViewGroup) findViewById(R.id.container);

        ViewCompat.animate(logoImageView)
                .translationY(-250)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (!(v instanceof Button)) {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(1000);
            } else {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(500);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_DesignDemo);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    void login() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivityForResult(intent, LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOGIN:
                if (resultCode == AuthActivity.AUTH_DENIED) {
                    Log.e("driclient", getTag() + " user deny the authentications");
                    return;
                }
                if (resultCode == AuthActivity.AUTH_FAILED) {
                    String msg = data.getStringExtra(AuthActivity.ERR_AUTH_MSG);
                    Log.e("driclient", getTag() + " login failed " + msg);
                    return;
                }
                if (resultCode == RESULT_CANCELED) {
                    return;
                }
                User authUser = (User) data.getSerializableExtra(AuthActivity.AUTH_USER);
                AccessToken token = (AccessToken) data.getSerializableExtra(AuthActivity.TOKEN);
                OAuthUser user = new OAuthUser();
                user.setAccessToken(token);
                user.setUser(authUser);
                Log.i("driclient", getTag() + " get user " + authUser.getJson() + "successfully");
                UserManager.setCurrentUser(user);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }


}
