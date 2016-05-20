package com.hustunique.jianguo.dribile.models;

/**
 * Created by JianGuo on 3/29/16.
 * POJO for bucket
 */
public class Buckets extends BaseBean {
    private String id;
    private String name;
    private String description;
    private String shots_count;
    private String created_at;
    private String updated_at;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getShots_count() {
        return shots_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

}
