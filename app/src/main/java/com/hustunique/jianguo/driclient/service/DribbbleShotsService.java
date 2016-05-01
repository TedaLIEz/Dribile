package com.hustunique.jianguo.driclient.service;

import com.hustunique.jianguo.driclient.models.Attachment;
import com.hustunique.jianguo.driclient.models.Buckets;
import com.hustunique.jianguo.driclient.models.Comments;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.service.api.Constants;

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
