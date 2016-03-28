package com.hustunique.jianguo.driclient.pojo;

/**
 * Created by JianGuo on 3/28/16.
 * Simple POJO for Dribbble user.
 */
public class User {
    private int id;
    private String name;
    private String username;
    private String html_url;
    private String avatar_url;
    private String buckets_url;
    private String followers_url;
    private String links_url;
    private String projects_url;
    private String shots_url;
    private String teams_url;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", html_url='" + html_url + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", buckets_url='" + buckets_url + '\'' +
                ", followers_url='" + followers_url + '\'' +
                ", links_url='" + links_url + '\'' +
                ", projects_url='" + projects_url + '\'' +
                ", shots_url='" + shots_url + '\'' +
                ", teams_url='" + teams_url + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }


    public String getBuckets_url() {
        return buckets_url;
    }

    public void setBuckets_url(String buckets_url) {
        this.buckets_url = buckets_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getLinks_url() {
        return links_url;
    }

    public void setLinks_url(String links_url) {
        this.links_url = links_url;
    }

    public String getProjects_url() {
        return projects_url;
    }

    public void setProjects_url(String projects_url) {
        this.projects_url = projects_url;
    }

    public String getShots_url() {
        return shots_url;
    }

    public void setShots_url(String shots_url) {
        this.shots_url = shots_url;
    }

    public String getTeams_url() {
        return teams_url;
    }

    public void setTeams_url(String teams_url) {
        this.teams_url = teams_url;
    }
}
