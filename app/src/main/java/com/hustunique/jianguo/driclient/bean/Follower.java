package com.hustunique.jianguo.driclient.bean;


/**
 * Created by JianGuo on 3/29/16.
 * POJO for followers
 */
public class Follower extends BaseBean {
    private String id;
    private String created_at;

    private User follower;


    public Follower(){}

    public String getId() {
        return id;
    }


    public String getCreated_at() {
        return created_at;
    }


    public User getFollower() {
        return follower;
    }

}
