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

import android.util.Base64;

import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.models.AccessToken;
import com.hustunique.jianguo.dribile.service.api.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JianGuo on 3/30/16.
 * Wrapper class for creating service
 */
public class ApiServiceFactory extends ServiceFactory {

    private static Retrofit.Builder builder
            = new Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());


    /**
     * Create service via {@link Constants} api url, take the {@link UserManager#getCurrentToken()}
     * token as default.
     * @param serviceClass the service class
     * @param <S> the class name
     * @return the service interface
     */
    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, MyAccountManager.getCurrentToken());
    }


    /**
     * Create service using auth access token
     * @param serviceClass the service interface
     * @param token the access token
     * @param <S> Class name
     * @return the service
     */
    public static <S> S createService(Class<S> serviceClass, final AccessToken token) {
        if (token != null) {
            httpClient.addInterceptor(new MyInterceptor(token));
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    /**
     * Create service authorized by username and password
     * @param serviceClass the service interface
     * @param username username
     * @param password password
     * @param <S> the service class name
     * @return the service
     */
    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        if (username != null && password != null) {
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .header("Accept", "application/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }




}
