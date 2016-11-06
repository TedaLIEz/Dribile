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

import com.hustunique.jianguo.dribile.service.api.Constants;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by JianGuo on 4/13/16.
 * Request for liking a shot
 */
public interface DribbbleLikeService {

    /**
     * Whether you like this shot
     * @param id the shot's id
     * @return the responseBody, if status code is <tt>200</tt> then is liked, <tt>404</tt> if it isn't liked
     */
    @GET(Constants.URL_BASE_SHOTS + "{id}/" + Constants.URL_BASE_LIKE)
    Observable<Response<ResponseBody>> isLike(@Path("id") String id);


    /**
     * Like a shot
     * @param id the shot's id
     * @return the responseBody, if status code is <tt>201</tt> then success, other if failed
     */
    @POST(Constants.URL_BASE_SHOTS + "{id}/" + Constants.URL_BASE_LIKE)
    Observable<Response<ResponseBody>> like(@Path("id") String id);

    /**
     * Unlike a shot
     * @param id the shot's id
     * @return the responseBody, if status code is <tt>204</tt> then success, other if failed
     */
    @DELETE(Constants.URL_BASE_SHOTS + "{id}/" + Constants.URL_BASE_LIKE)
    Observable<Response<ResponseBody>> unlike(@Path("id") String id);

}
