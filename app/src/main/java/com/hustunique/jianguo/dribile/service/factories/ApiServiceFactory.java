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

import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.models.AccessToken;
import com.hustunique.jianguo.dribile.service.api.Constants;

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
    public static <S> S createService(@NonNull Class<S> serviceClass) {
        return createService(serviceClass, MyAccountManager.getCurrentToken());
    }


    /**
     * Create service using auth access token
     * @param serviceClass the service interface
     * @param token the access token
     * @param <S> Class name
     * @return the service
     */
    public static <S> S createService(@NonNull Class<S> serviceClass, @NonNull final AccessToken token) {
        Retrofit retrofit = builder.client(DribileClientFactory.createTokenClient(token)).build();
        return retrofit.create(serviceClass);
    }




}
