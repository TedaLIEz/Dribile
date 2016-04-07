package com.hustunique.jianguo.driclient.service;

import com.hustunique.jianguo.driclient.bean.Attachment;
import com.hustunique.jianguo.driclient.bean.Buckets;
import com.hustunique.jianguo.driclient.bean.Comments;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.api.Constants;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
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
     * @return comments in this shot.
     */
    @GET(Constants.URL_BASE_SHOTS + "{id}/" + Constants.URL_BASE_COMMENTS)
    Observable<List<Comments>> getComment(@Path("id") String id);

    @GET(Constants.URL_BASE_SHOTS + "{id}/" + Constants.URL_BASE_ATTACHMENTS)
    Observable<List<Attachment>> getAttachments(@Path("id") String id);
}
