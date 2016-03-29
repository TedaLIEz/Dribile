package com.hustunique.jianguo.driclient.bean;

/**
 * Created by JianGuo on 3/29/16.
 * POJO for comment in shots
 */
public class Comments {
    private int id;
    private String body;
    private int likes_count;
    private String links_url;
    private String created_at;
    private String updated_at;

    // who leaves this comment
    private User user;

}
