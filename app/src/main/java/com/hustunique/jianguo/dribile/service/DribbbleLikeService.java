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
