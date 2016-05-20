package com.hustunique.jianguo.dribile.service.api;

/**
 * Created by JianGuo on 3/29/16.
 */
public class Constants {
    public static final String URL_BASE = "https://api.dribbble.com/v1/";
    public static final String URL_BASE_COMMENTS = "comments/";
    public static final String URL_BASE_USERS = "users/";
    public static final String URL_BASE_FOLLOWERS = "followers/";
    public static final String URL_BASE_FOLLOWING = "following/";
    public static final String URL_BASE_ATTACHMENTS = "attachments/";
    public static final String URL_BASE_LIKE = "like/";
    public static final String URL_BASE_LIKES = "likes/";
    public static final String URL_BASE_FOLLOW = "follow/";

    public class OAuth {
        public static final String URL_BASE_OAUTH = "https://dribbble.com/oauth/";

        public static final String URL_AUTHORIZE = URL_BASE_OAUTH + "authorize";

        public static final String URL_TOKEN = URL_BASE_OAUTH + "token";

        public static final String URL_AUTH_USER = "user/";
    }

    public static final String URL_BASE_SHOTS = "shots/";

    public static final String URL_BASE_BUCKETS = "buckets/";

}