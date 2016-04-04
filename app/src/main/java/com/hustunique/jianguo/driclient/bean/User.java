package com.hustunique.jianguo.driclient.bean;

/**
 * Created by JianGuo on 3/28/16.
 * Simple POJO for Dribbble user.
 */
public class User extends BaseBean {
    private String id;

    private String name;

    private String username;

    private String html_url;

    private String avatar_url;

    private String bio;

    private String location;

    private String buckets_count;

    private String comments_received_count;

    private String followers_count;

    private String followings_count;

    private String likes_count;

    private String likes_received_count;

    private String projects_count;

    private String rebounds_received_count;

    private String shots_count;

    private String teams_count;

    private String can_upload_shot;

    private String type;

    private String pro;

    private String buckets_url;

    private String followers_url;

    private String following_url;

    private String likes_url;

    private String shots_url;

    private String teams_url;

    private String created_at;

    private String update_at;

    public class links {
        private String web;

        private String twitter;

        public String getWeb() {
            return web;
        }


        public String getTwitter() {
            return twitter;
        }

    }

    private links link;

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

    public String getProjects_count() {
        return projects_count;
    }

    public String getRebounds_received_count() {
        return rebounds_received_count;
    }

    public String getShots_count() {
        return shots_count;
    }

    public String getTeams_count() {
        return teams_count;
    }

    public String getCan_upload_shot() {
        return can_upload_shot;
    }

    public String getType() {
        return type;
    }

    public String getPro() {
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

    public String getShots_url() {
        return shots_url;
    }

    public String getTeams_url() {
        return teams_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public links getLink() {
        return link;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!name.equals(user.name)) return false;
        if (!username.equals(user.username)) return false;
        if (!html_url.equals(user.html_url)) return false;
        if (!avatar_url.equals(user.avatar_url)) return false;
        if (!bio.equals(user.bio)) return false;
        if (!location.equals(user.location)) return false;
        if (!buckets_count.equals(user.buckets_count)) return false;
        if (!comments_received_count.equals(user.comments_received_count)) return false;
        if (!followers_count.equals(user.followers_count)) return false;
        if (!followings_count.equals(user.followings_count)) return false;
        if (!likes_count.equals(user.likes_count)) return false;
        if (!likes_received_count.equals(user.likes_received_count)) return false;
        if (!projects_count.equals(user.projects_count)) return false;
        if (!rebounds_received_count.equals(user.rebounds_received_count)) return false;
        if (!shots_count.equals(user.shots_count)) return false;
        if (!teams_count.equals(user.teams_count)) return false;
        if (!can_upload_shot.equals(user.can_upload_shot)) return false;
        if (!type.equals(user.type)) return false;
        if (!pro.equals(user.pro)) return false;
        if (!buckets_url.equals(user.buckets_url)) return false;
        if (!followers_url.equals(user.followers_url)) return false;
        if (!following_url.equals(user.following_url)) return false;
        if (!likes_url.equals(user.likes_url)) return false;
        if (!shots_url.equals(user.shots_url)) return false;
        if (!teams_url.equals(user.teams_url)) return false;
        if (!created_at.equals(user.created_at)) return false;
        if (!update_at.equals(user.update_at)) return false;
        return link.equals(user.link);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + html_url.hashCode();
        result = 31 * result + avatar_url.hashCode();
        result = 31 * result + bio.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + buckets_count.hashCode();
        result = 31 * result + comments_received_count.hashCode();
        result = 31 * result + followers_count.hashCode();
        result = 31 * result + followings_count.hashCode();
        result = 31 * result + likes_count.hashCode();
        result = 31 * result + likes_received_count.hashCode();
        result = 31 * result + projects_count.hashCode();
        result = 31 * result + rebounds_received_count.hashCode();
        result = 31 * result + shots_count.hashCode();
        result = 31 * result + teams_count.hashCode();
        result = 31 * result + can_upload_shot.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + pro.hashCode();
        result = 31 * result + buckets_url.hashCode();
        result = 31 * result + followers_url.hashCode();
        result = 31 * result + following_url.hashCode();
        result = 31 * result + likes_url.hashCode();
        result = 31 * result + shots_url.hashCode();
        result = 31 * result + teams_url.hashCode();
        result = 31 * result + created_at.hashCode();
        result = 31 * result + update_at.hashCode();
        result = 31 * result + link.hashCode();
        return result;
    }
}
