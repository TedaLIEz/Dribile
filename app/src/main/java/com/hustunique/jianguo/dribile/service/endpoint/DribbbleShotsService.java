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

import com.hustunique.jianguo.dribile.models.Attachment;
import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.models.Comments;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.service.api.Constants;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by JianGuo on 3/29/16.
 * Request for dribbble shots
 */
public interface DribbbleShotsService {
    /**
     * Get all the shots
     * @param params query params
     * @return all shots.
     */
    @GET(Constants.URL_BASE_SHOTS)
    Observable<List<Shots>> getAllShots(@QueryMap Map<String, String> params);

    /**
     * Get shot by id
     * @param id the shot id
     * @return the shot
     */
    @GET(Constants.URL_BASE_SHOTS + "{id}")
    Observable<Shots> getShotById(@Path("id") String id);

    /**
     * Get buckets which includes the shot
     * @param id the shot id
     * @return buckets including the shot.
     */
    @GET(Constants.URL_BASE_SHOTS + "{id}" + Constants.URL_BASE_BUCKETS)
    Observable<Buckets> getBuckets(@Path("id") String id);

    /**
     * Get comments in the shot
     * @param id the shot's id
     * @param params other query params
     * @return comments in this shot.
     */
    @GET(Constants.URL_BASE_SHOTS + "{id}/" + Constants.URL_BASE_COMMENTS)
    Observable<List<Comments>> getComment(@Path("id") String id, @QueryMap Map<String, String> params);

    /**
     * Get comments in the shot
     * @param id the shot's id
     * @return comments in this shot, get first 12 comments by default
     */
    @GET(Constants.URL_BASE_SHOTS + "{id}/" + Constants.URL_BASE_COMMENTS)
    Observable<List<Comments>> getComment(@Path("id") String id);

    @FormUrlEncoded
    @POST(Constants.URL_BASE_SHOTS + "{id}/" + Constants.URL_BASE_COMMENTS)
    Observable<Comments> addComment(@Path("id") String id, @Field("body") String body);


    @GET(Constants.URL_BASE_SHOTS + "{id}/" + Constants.URL_BASE_ATTACHMENTS)
    Observable<List<Attachment>> getAttachments(@Path("id") String id);


}
