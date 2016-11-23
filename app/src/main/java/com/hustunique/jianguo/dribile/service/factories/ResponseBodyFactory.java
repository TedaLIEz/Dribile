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

/**
 * Created by JianGuo on 4/13/16.
 * Factory for creating simple service without java bean.
 */
public class ResponseBodyFactory extends ServiceFactory {
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());


    public static <S> S createService(@NonNull Class<S> serviceClass) {
        return createService(serviceClass, MyAccountManager.getCurrentToken());
    }

    public static <S> S createService(@NonNull Class<S> serviceClass, @NonNull AccessToken token) {
        Retrofit retrofit = builder.client(DribileClientFactory.createTokenClient(token)).build();
        return retrofit.create(serviceClass);
    }
}
