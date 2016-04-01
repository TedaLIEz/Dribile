package com.hustunique.jianguo.driclient.bean;

/**
 * Created by JianGuo on 3/29/16.
 * POJO for bucket
 */
public class Buckets extends BaseBean {
    private int id;
    private String name;
    private String description;
    private int shots_count;
    private String created_at;
    private String updated_at;

    //Owner of this bucket
    private User user;


}
