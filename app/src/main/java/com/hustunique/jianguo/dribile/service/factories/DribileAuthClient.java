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

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by JianGuo on 11/23/16.
 * Deprecated, use {@link DribileClientFactory} instead
 */
@Deprecated
public class DribileAuthClient {
    private static final long TIMEOUT_CONNECT = 30 * 1000;

    private static OkHttpClient client;

    public static OkHttpClient create() {
        if (client == null) {
            client = new OkHttpClient.Builder().connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
                    .build();
        }
        return client;
    }
}
