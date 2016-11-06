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

import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.service.api.Constants;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by JianGuo on 3/29/16.
 * Dribbble buckets service
 */
public interface DribbbleBucketsService {
    /**
     * Get shots for a buckets
     * @param id the bucket id
     * @return the shot list
     */
    @GET(Constants.URL_BASE_BUCKETS + "{id}/" + Constants.URL_BASE_SHOTS)
    Observable<List<Shots>> getShotsFromBuckets(@Path("id") String id, @QueryMap Map<String, String> params);

    /**
     * put a shot into bucket
     * @param id the shot id
     * @return the response
     */
    @FormUrlEncoded
    @PUT(Constants.URL_BASE_BUCKETS + "{id}/" + Constants.URL_BASE_SHOTS)
    Observable<Response<ResponseBody>> putShotInBucket(@Path("id") String id, @Field("shot_id") String shotId);


    /**
     * Delete bucket by id
     * @param id the bucket id
     * @return the response
     */
    @DELETE(Constants.URL_BASE_BUCKETS + "{id}/")
    Observable<Response<ResponseBody>> deleteBucket(@Path("id") String id);

    @FormUrlEncoded
    @POST(Constants.URL_BASE_BUCKETS)
    Observable<Buckets> createBucket(@Field("name") String name, @Field("description") String description);
}
