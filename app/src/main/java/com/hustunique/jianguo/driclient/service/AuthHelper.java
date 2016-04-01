package com.hustunique.jianguo.driclient.service;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hustunique.jianguo.driclient.app.MyApp;
import com.hustunique.jianguo.driclient.bean.AccessToken;
import com.hustunique.jianguo.driclient.service.factories.AuthServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JianGuo on 3/30/16.
 * Helper class to get token via OAuth
 */
public class AuthHelper {

    private static AccessToken accessToken;

    public static AccessToken getAccessToken() {
        return accessToken;
    }

    public static void saveToken(AccessToken token) {
        if (accessToken == null) {
            accessToken = token;
        }

    }


    /**
     * Get accesstoken
     * @param uri the uri
     * @return the accessToken
     */
//    @Deprecated
//    public static void parseToken(@NonNull Uri uri) {
//        AccessToken accessToken = null;
//        String code = uri.getQueryParameter("code");
//        if (code != null) {
//            DribbbleAuthService authService = AuthServiceFactory.createAuthService(DribbbleAuthService.class);
//            Call<AccessToken> call = authService.getAccessToken(MyApp.client_id, MyApp.client_secret, code);
//                call.enqueue(new Callback<AccessToken>() {
//                    @Override
//                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
//                        Log.i("driclient", response.body() + "");
//                    }
//                    @Override
//                    public void onFailure(Call<AccessToken> call, Throwable t) {
//                        Log.e("driclient", "error" + t.getMessage());
//                    }
//                });
//        } else if (uri.getQueryParameter("error") != null) {
//            Log.e("dirclient", "error when get token");
//        }
//    }
}
