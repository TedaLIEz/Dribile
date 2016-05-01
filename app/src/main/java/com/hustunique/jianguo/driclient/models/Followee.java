package com.hustunique.jianguo.driclient.models;

/**
 * Created by JianGuo on 3/29/16.
 */
public class Followee extends BaseBean {
    private String id;
    private String created_at;
    private User followee;

    public Followee(){}

    public String getId() {
        return id;
    }


    public String getCreated_at() {
        return created_at;
    }


    public User getFollowee() {
        return followee;
    }

}
