package com.hustunique.jianguo.driclient.presenters;

import android.content.Intent;
import android.util.Log;

import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.models.AccessToken;
import com.hustunique.jianguo.driclient.models.OAuthUser;
import com.hustunique.jianguo.driclient.models.User;
import com.hustunique.jianguo.driclient.ui.activity.AuthActivity;
import com.hustunique.jianguo.driclient.ui.activity.MainActivity;
import com.hustunique.jianguo.driclient.views.LoginView;

/**
 * Created by JianGuo on 5/1/16.
 * Presenter for LoginActivity
 */
public class LoginPresenter extends BasePresenter<OAuthUser, LoginView> {
    public static int LOGIN = 0x00000000;

    @Override
    protected void updateView() {
        Intent intent = new Intent(view().getRef(), MainActivity.class);
        view().getRef().startActivity(intent);
        view().getRef().finish();
    }

    public void login(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN) {
            switch (resultCode) {
                case AuthPresenter.AUTH_DENIED:
                    Log.e("driclient"," user deny the authentications");
                    break;
                case AuthPresenter.AUTH_FAILED:
                    String msg = data.getStringExtra(AuthPresenter.ERR_AUTH_MSG);
                    Log.e("driclient", " login failed " + msg);
                    break;
                case AuthPresenter.AUTH_CANCELED:
                    break;
                case AuthPresenter.AUTH_OK:
                    User authUser = (User) data.getSerializableExtra(AuthPresenter.AUTH_USER);
                    AccessToken token = (AccessToken) data.getSerializableExtra(AuthPresenter.TOKEN);
                    OAuthUser user = new OAuthUser();
                    user.setAccessToken(token);
                    user.setUser(authUser);
                    Log.i("driclient", " get user " + authUser.getJson() + "successfully");
                    UserManager.setCurrentUser(user);
                    setModel(user);
                    break;
                default:
                    break;
            }
        }
    }


    public void auth() {
        view().getRef().startActivityForResult(new Intent(view().getRef(), AuthActivity.class), LOGIN);
    }


}
