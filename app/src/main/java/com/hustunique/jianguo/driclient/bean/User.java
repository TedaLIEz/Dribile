package com.hustunique.jianguo.driclient.bean;

/**
 * Created by JianGuo on 3/28/16.
 * Simple POJO for Dribbble user.
 */
public class User extends BaseBean {
    private int id;

    private String name;

    private String username;

    private String html_url;

    private String avatar_url;

    private String bio;

    private String location;

    private int buckets_count;

    private int comments_received_count;

    private int followers_count;

    private int followings_count;

    private int likes_count;

    private int likes_received_count;

    private int projects_count;

    private int rebounds_received_count;

    private int shots_count;

    private int teams_count;

    private boolean can_upload_shot;

    private String type;

    private boolean pro;

    private String buckets_url;

    private String followers_url;

    private String following_url;

    private String likes_url;

    private String shots_url;

    private String teams_url;

    private String created_at;

    private String update_at;

    private class links {
        private String web;

        private String twitter;

        public String getWeb() {
            return web;
        }

        public void setWeb(String web) {
            this.web = web;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        @Override
        public String toString() {
            return "links{" +
                    "web='" + web + '\'' +
                    ", twitter='" + twitter + '\'' +
                    '}';
        }
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getBuckets_count() {
        return buckets_count;
    }

    public void setBuckets_count(int buckets_count) {
        this.buckets_count = buckets_count;
    }

    public int getComments_received_count() {
        return comments_received_count;
    }

    public void setComments_received_count(int comments_received_count) {
        this.comments_received_count = comments_received_count;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFollowings_count() {
        return followings_count;
    }

    public void setFollowings_count(int followings_count) {
        this.followings_count = followings_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getLikes_received_count() {
        return likes_received_count;
    }

    public void setLikes_received_count(int likes_received_count) {
        this.likes_received_count = likes_received_count;
    }

    public int getProjects_count() {
        return projects_count;
    }

    public void setProjects_count(int projects_count) {
        this.projects_count = projects_count;
    }

    public int getRebounds_received_count() {
        return rebounds_received_count;
    }

    public void setRebounds_received_count(int rebounds_received_count) {
        this.rebounds_received_count = rebounds_received_count;
    }

    public int getShots_count() {
        return shots_count;
    }

    public void setShots_count(int shots_count) {
        this.shots_count = shots_count;
    }

    public int getTeams_count() {
        return teams_count;
    }

    public void setTeams_count(int teams_count) {
        this.teams_count = teams_count;
    }

    public boolean isCan_upload_shot() {
        return can_upload_shot;
    }

    public void setCan_upload_shot(boolean can_upload_shot) {
        this.can_upload_shot = can_upload_shot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPro() {
        return pro;
    }

    public void setPro(boolean pro) {
        this.pro = pro;
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

    public String getFollowing_url() {
        return following_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public void setLikes_url(String likes_url) {
        this.likes_url = likes_url;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public User() {
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", html_url='" + html_url + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", buckets_count=" + buckets_count +
                ", comments_received_count=" + comments_received_count +
                ", followers_count=" + followers_count +
                ", followings_count=" + followings_count +
                ", likes_count=" + likes_count +
                ", likes_received_count=" + likes_received_count +
                ", projects_count=" + projects_count +
                ", rebounds_received_count=" + rebounds_received_count +
                ", shots_count=" + shots_count +
                ", teams_count=" + teams_count +
                ", can_upload_shot=" + can_upload_shot +
                ", type='" + type + '\'' +
                ", pro=" + pro +
                ", buckets_url='" + buckets_url + '\'' +
                ", followers_url='" + followers_url + '\'' +
                ", following_url='" + following_url + '\'' +
                ", likes_url='" + likes_url + '\'' +
                ", shots_url='" + shots_url + '\'' +
                ", teams_url='" + teams_url + '\'' +
                ", created_at='" + created_at + '\'' +
                ", update_at='" + update_at + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (buckets_count != user.buckets_count) return false;
        if (comments_received_count != user.comments_received_count) return false;
        if (followers_count != user.followers_count) return false;
        if (followings_count != user.followings_count) return false;
        if (likes_count != user.likes_count) return false;
        if (likes_received_count != user.likes_received_count) return false;
        if (projects_count != user.projects_count) return false;
        if (rebounds_received_count != user.rebounds_received_count) return false;
        if (shots_count != user.shots_count) return false;
        if (teams_count != user.teams_count) return false;
        if (can_upload_shot != user.can_upload_shot) return false;
        if (pro != user.pro) return false;
        if (!name.equals(user.name)) return false;
        if (!username.equals(user.username)) return false;
        if (html_url != null ? !html_url.equals(user.html_url) : user.html_url != null)
            return false;
        if (avatar_url != null ? !avatar_url.equals(user.avatar_url) : user.avatar_url != null)
            return false;
        if (bio != null ? !bio.equals(user.bio) : user.bio != null) return false;
        if (location != null ? !location.equals(user.location) : user.location != null)
            return false;
        if (type != null ? !type.equals(user.type) : user.type != null) return false;
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
        return update_at.equals(user.update_at);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + (html_url != null ? html_url.hashCode() : 0);
        result = 31 * result + (avatar_url != null ? avatar_url.hashCode() : 0);
        result = 31 * result + (bio != null ? bio.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + buckets_count;
        result = 31 * result + comments_received_count;
        result = 31 * result + followers_count;
        result = 31 * result + followings_count;
        result = 31 * result + likes_count;
        result = 31 * result + likes_received_count;
        result = 31 * result + projects_count;
        result = 31 * result + rebounds_received_count;
        result = 31 * result + shots_count;
        result = 31 * result + teams_count;
        result = 31 * result + (can_upload_shot ? 1 : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (pro ? 1 : 0);
        result = 31 * result + (buckets_url != null ? buckets_url.hashCode() : 0);
        result = 31 * result + (followers_url != null ? followers_url.hashCode() : 0);
        result = 31 * result + (following_url != null ? following_url.hashCode() : 0);
        result = 31 * result + (likes_url != null ? likes_url.hashCode() : 0);
        result = 31 * result + (shots_url != null ? shots_url.hashCode() : 0);
        result = 31 * result + (teams_url != null ? teams_url.hashCode() : 0);
        result = 31 * result + created_at.hashCode();
        result = 31 * result + update_at.hashCode();
        return result;
    }
}
