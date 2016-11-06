/*
 * Copyright (c) 2016.  TedaLIEz <aliezted@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hustunique.jianguo.dribile.service;

import com.hustunique.jianguo.dribile.models.AccessToken;

/**
 * Created by JianGuo on 3/30/16.
 * Helper class to get token via OAuth
 */
@Deprecated
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
