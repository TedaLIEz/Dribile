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

import com.hustunique.jianguo.dribile.models.AccessToken;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by JianGuo on 3/30/16.
 * Skeleton class for service factory
 */
abstract class ServiceFactory {
    protected static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    static class MyInterceptor implements Interceptor {

        private AccessToken mToken;
        MyInterceptor(AccessToken token) {
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
}
