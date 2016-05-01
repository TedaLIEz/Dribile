package com.hustunique.jianguo.driclient.models;

/**
 * Created by JianGuo on 3/29/16.
 * POJO for comment in shots
 */
public class Comments extends BaseBean {
    private String id;
    private String body;
    private String likes_count;
    private String links_url;
    private String created_at;
    private String updated_at;

    // who leaves this comment
    private User user;

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public String getLinks_url() {
        return links_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public User getUser() {
        return user;
    }
}
