package com.hustunique.jianguo.dribile.models;

import java.io.Serializable;

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

    public class Links implements Serializable {
        private String web;

        private String twitter;

        public String getWeb() {
            return web;
        }


        public String getTwitter() {
            return twitter;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Links links = (Links) o;

            if (web != null ? !web.equals(links.web) : links.web != null) return false;
            return twitter != null ? twitter.equals(links.twitter) : links.twitter == null;

        }

        @Override
        public int hashCode() {
            int result = web != null ? web.hashCode() : 0;
            result = 31 * result + (twitter != null ? twitter.hashCode() : 0);
            return result;
        }
    }

    private Links links;

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

    public Links getLink() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!name.equals(user.name)) return false;
        if (!username.equals(user.username)) return false;
        if (html_url != null ? !html_url.equals(user.html_url) : user.html_url != null)
            return false;
        if (avatar_url != null ? !avatar_url.equals(user.avatar_url) : user.avatar_url != null)
            return false;
        if (bio != null ? !bio.equals(user.bio) : user.bio != null) return false;
        if (location != null ? !location.equals(user.location) : user.location != null)
            return false;
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
        if (can_upload_shot != null ? !can_upload_shot.equals(user.can_upload_shot) : user.can_upload_shot != null)
            return false;
        if (!type.equals(user.type)) return false;
        if (!pro.equals(user.pro)) return false;
        if (buckets_url != null ? !buckets_url.equals(user.buckets_url) : user.buckets_url != null)
            return false;
        if (followers_url != null ? !followers_url.equals(user.followers_url) : user.followers_url != null)
            return false;
        if (following_url != null ? !following_url.equals(user.following_url) : user.following_url != null)
            return false;
        if (likes_url != null ? !likes_url.equals(user.likes_url) : user.likes_url != null)
            return false;
        if (shots_url != null ? !shots_url.equals(user.shots_url) : user.shots_url != null)
            return false;
        if (teams_url != null ? !teams_url.equals(user.teams_url) : user.teams_url != null)
            return false;
        if (!created_at.equals(user.created_at)) return false;
        if (update_at != null ? !update_at.equals(user.update_at) : user.update_at != null)
            return false;
        return links != null ? links.equals(user.links) : user.links == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + (html_url != null ? html_url.hashCode() : 0);
        result = 31 * result + (avatar_url != null ? avatar_url.hashCode() : 0);
        result = 31 * result + (bio != null ? bio.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
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
        result = 31 * result + (can_upload_shot != null ? can_upload_shot.hashCode() : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + pro.hashCode();
        result = 31 * result + (buckets_url != null ? buckets_url.hashCode() : 0);
        result = 31 * result + (followers_url != null ? followers_url.hashCode() : 0);
        result = 31 * result + (following_url != null ? following_url.hashCode() : 0);
        result = 31 * result + (likes_url != null ? likes_url.hashCode() : 0);
        result = 31 * result + (shots_url != null ? shots_url.hashCode() : 0);
        result = 31 * result + (teams_url != null ? teams_url.hashCode() : 0);
        result = 31 * result + created_at.hashCode();
        result = 31 * result + update_at.hashCode();
        result = 31 * result + (links != null ? links.hashCode() : 0);
        return result;
    }
}
