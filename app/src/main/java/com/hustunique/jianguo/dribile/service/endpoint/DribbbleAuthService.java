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

package com.hustunique.jianguo.dribile.service.endpoint;

import com.hustunique.jianguo.dribile.models.AccessToken;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by JianGuo on 3/28/16.
 * Dribbble OAuth Service
 */
public interface DribbbleAuthService {

    /**
     * Get token
     * @param clientId the client_id
     * @param secret the client_secret
     * @param code the code
     * @return the AccessToken
     */
    @FormUrlEncoded
    @POST("token")
    Observable<AccessToken> getAccessToken(@Field("client_id") String clientId,
                                           @Field("client_secret") String secret,
                                           @Field("code") String code);
}
