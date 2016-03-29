package com.hustunique.jianguo.driclient.bean;


/**
 * Created by JianGuo on 3/29/16.
 * POJO for followers
 */
public class Follower {
    private int id;
    private String created_at;

    private User follower;


    public Follower(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}
