package com.hustunique.jianguo.driclient.bean;

/**
 * Created by JianGuo on 3/29/16.
 * POJO for team
 */
public class Team extends BaseBean {
    private String id;
    private String name;
    private String username;
    private String html_url;
    private String avatar_url;
    private String bio;
    private String location;

    private class links {
        private String web;
        private String twitter;
    }

    private String buckets_count;
    private String comments_received_count;
    private String followers_count;
    private String followings_count;
    private String likes_count;
    private String likes_received_count;
    private String members_count;
    private String projects_count;
    private String rebounds_received_count;
    private String shots_count;

    private String can_upload_shot;
    private String type;
    private String pro;

    private String buckets_url;
    private String followers_url;
    private String following_url;
    private String likes_url;
    private String members_url;
    private String shots_url;
    private String team_shots_url;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getUsername() {
        return username;
    }


    public String getHtml_url() {
        return html_url;
    }


    public String getAvatar_url() {
        return avatar_url;
    }


    public String getBio() {
        return bio;
    }


    public String getLocation() {
        return location;
    }

    public String getBuckets_count() {
        return buckets_count;
    }


    public String getComments_received_count() {
        return comments_received_count;
    }



    public String getFollowers_count() {
        return followers_count;
    }


    public String getFollowings_count() {
        return followings_count;
    }



    public String getLikes_count() {
        return likes_count;
    }


    public String getLikes_received_count() {
        return likes_received_count;
    }


    public String getMembers_count() {
        return members_count;
    }


    public String getProjects_count() {
        return projects_count;
    }


    public String getRebounds_received_count() {
        return rebounds_received_count;
    }

    public String getShots_count() {
        return shots_count;
    }


    public String isCan_upload_shot() {
        return can_upload_shot;
    }

    public String getType() {
        return type;
    }


    public String isPro() {
        return pro;
    }


    public String getBuckets_url() {
        return buckets_url;
    }


    public String getFollowers_url() {
        return followers_url;
    }


    public String getFollowing_url() {
        return following_url;
    }


    public String getLikes_url() {
        return likes_url;
    }


    public String getMembers_url() {
        return members_url;
    }


    public String getShots_url() {
        return shots_url;
    }


    public String getTeam_shots_url() {
        return team_shots_url;
    }


    public String getCreated_at() {
        return created_at;
    }


    public String getUpdated_at() {
        return updated_at;
    }

}
