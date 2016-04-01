package com.hustunique.jianguo.driclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.bean.AccessToken;
import com.hustunique.jianguo.driclient.bean.OAuthUser;
import com.hustunique.jianguo.driclient.bean.User;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Login Activity for application
 */
public class LoginActivity extends BaseActivity {
    private static final int LOGIN = 0x00000000;
    @Bind(R.id.btn_login)
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                if (resultCode != RESULT_OK) {
                    return;
                }
                AccessToken token = (AccessToken) data.getSerializableExtra(AuthActivity.TOKEN);
                onLoginSuccess(token);
                break;
            default:
                break;
        }
    }


    private void onLoginSuccess(@NonNull final AccessToken token) {
        DribbbleUserService dribbbleUserService = ApiServiceFactory.createService(DribbbleUserService.class, token);
        dribbbleUserService.getAuthUser()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.i("driclient", "get user completed");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(User user) {
                        //TODO: Save the auth user to database, but will token be expired ?
                        //TODO: Save authUser to Accounts
                        OAuthUser oAuthUser = new OAuthUser();
                        oAuthUser.setUser(user);
                        oAuthUser.setAccessToken(token);
                        Log.i("driclient", "get user" + user);
                    }
                });
    }

}
