package com.hustunique.jianguo.driclient.bean;

import java.util.ArrayList;

/**
 * Created by JianGuo on 3/29/16.
 */
public class Shots {
    private int id;
    private String title;
    private String description;
    private int width;
    private int height;
    private class Images {
        private int hidpi;
        private String normal;
        private String teaser;
    }

    private int views_count;
    private int likes_count;
    private int comments_count;
    private int attachments_count;
    private int rebounds_count;
    private int buckets_count;

    private String created_at;
    private String updated_at;
    private String attachments_url;
    private String buckets_url;
    private String comments_url;
    private String likes_url;
    private String projects_url;
    private String rebounds_url;
    private boolean animated;
    private ArrayList<String> tags;
    // Owner of this shot
    private User user;
    // team to which this shot belongs
    private Team team;

}
