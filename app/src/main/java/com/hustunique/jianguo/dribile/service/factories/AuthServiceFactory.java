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

import com.hustunique.jianguo.dribile.service.api.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JianGuo on 3/30/16.
 * Service Factory creating auth service
 */
public class AuthServiceFactory extends ServiceFactory {

    private static Retrofit.Builder authBuilder
            = new Retrofit.Builder()
            .baseUrl(Constants.OAuth.URL_BASE_OAUTH)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    /**
     * Create auth service via {@link com.hustunique.jianguo.dribile.service.api.Constants.OAuth}
     * @param serviceClass the service class
     * @param <S> class name
     * @return the service
     */
    public static <S> S createAuthService(Class<S> serviceClass) {
        Retrofit retrofit = authBuilder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

}
