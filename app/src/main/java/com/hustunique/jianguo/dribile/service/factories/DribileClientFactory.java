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

package com.hustunique.jianguo.dribile.service.factories;

import android.support.annotation.NonNull;

import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.AccessToken;
import com.hustunique.jianguo.dribile.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by JianGuo on 11/23/16.
 * Factory class for creating {@link OkHttpClient} in application with singleton pattern.
 */

public class DribileClientFactory {
    private static OkHttpClient mTokenClient, mAuthClient;

    private static final long TIMEOUT_CONNECT = 30 * 1000;
    private static final long CACHE_SIZE = 1024 * 1024 * 50;

    public static OkHttpClient createBasicClient() {
        if (mAuthClient == null) {
            mAuthClient = cacheBuilder().build();
        }
        return mAuthClient;
    }

    public static OkHttpClient createTokenClient(@NonNull AccessToken token) {
        if (mTokenClient == null) {
            mTokenClient = cacheBuilder().addInterceptor(new TokenInterceptor(token)).build();
        }
        return mTokenClient;
    }

    private static class TokenInterceptor implements Interceptor {

        private AccessToken mToken;

        TokenInterceptor(@NonNull AccessToken token) {
            mToken = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", mToken.toString())
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }

    private static OkHttpClient.Builder cacheBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        File cachedFile = new File(AppData.getContext().getCacheDir(), "dribile_cache");
        Cache cache = new Cache(cachedFile, CACHE_SIZE);
        return builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS).cache(cache)
                .addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtils.isNetworkAccessable(AppData.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response originalResponse = chain.proceed(request);

                if (NetUtils.isNetworkAccessable(AppData.getContext())) {

                    String cacheControl = request.cacheControl().toString();

                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .build();
                } else {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", CacheControl.FORCE_CACHE.toString())
                            .build();
                }
            }
        });
    }
}
