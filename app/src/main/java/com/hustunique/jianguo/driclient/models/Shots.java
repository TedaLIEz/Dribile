package com.hustunique.jianguo.driclient.models;

import java.util.ArrayList;

/**
 * Created by JianGuo on 3/29/16.
 */
public class Shots extends BaseBean {
    private String id;
    private String title;
    private String description;
    private String width;
    private String height;

    public class Images extends BaseBean {
        private String hidpi;
        private String normal;
        private String teaser;

        public String getHidpi() {
            return hidpi;
        }

        public String getNormal() {
            return normal;
        }

        public String getTeaser() {
            return teaser;
        }
    }

    private Images images;

    private String views_count;
    private String likes_count;
    private String comments_count;
    private String attachments_count;
    private String rebounds_count;
    private String buckets_count;

    private String created_at;
    private String updated_at;
    private String attachments_url;
    private String buckets_url;
    private String comments_url;
    private String likes_url;
    private String projects_url;
    private String rebounds_url;
    private String animated;
    private ArrayList<String> tags;
    private String html_url;
    // Owner of this shot
    private User user;
    // team to which this shot belongs
    private Team team;

    public String getHtml_url() {
        return html_url;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public Images getImages() {
        return images;
    }

    public String getViews_count() {
        return views_count;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public String getComments_count() {
        return comments_count;
    }

    public String getAttachments_count() {
        return attachments_count;
    }

    public String getRebounds_count() {
        return rebounds_count;
    }

    public String getBuckets_count() {
        return buckets_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getAttachments_url() {
        return attachments_url;
    }

    public String getBuckets_url() {
        return buckets_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public String getProjects_url() {
        return projects_url;
    }

    public String getRebounds_url() {
        return rebounds_url;
    }

    public String getAnimated() {
        return animated;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public User getUser() {
        return user;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shots shots = (Shots) o;

        if (!id.equals(shots.id)) return false;
        if (!title.equals(shots.title)) return false;
        if (description != null ? !description.equals(shots.description) : shots.description != null)
            return false;
        if (!width.equals(shots.width)) return false;
        if (!height.equals(shots.height)) return false;
        if (!images.equals(shots.images)) return false;
        if (!views_count.equals(shots.views_count)) return false;
        if (!likes_count.equals(shots.likes_count)) return false;
        if (!comments_count.equals(shots.comments_count)) return false;
        if (!attachments_count.equals(shots.attachments_count)) return false;
        if (!rebounds_count.equals(shots.rebounds_count)) return false;
        if (!buckets_count.equals(shots.buckets_count)) return false;
        if (!created_at.equals(shots.created_at)) return false;
        if (!updated_at.equals(shots.updated_at)) return false;
        if (!attachments_url.equals(shots.attachments_url)) return false;
        if (!buckets_url.equals(shots.buckets_url)) return false;
        if (!comments_url.equals(shots.comments_url)) return false;
        if (!likes_url.equals(shots.likes_url)) return false;
        if (!projects_url.equals(shots.projects_url)) return false;
        if (!rebounds_url.equals(shots.rebounds_url)) return false;
        if (!animated.equals(shots.animated)) return false;
        if (tags != null ? !tags.equals(shots.tags) : shots.tags != null) return false;
        if (user != null ? !user.equals(shots.user) : shots.user != null) return false;
        return team != null ? team.equals(shots.team) : shots.team == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + width.hashCode();
        result = 31 * result + height.hashCode();
        result = 31 * result + images.hashCode();
        result = 31 * result + views_count.hashCode();
        result = 31 * result + likes_count.hashCode();
        result = 31 * result + comments_count.hashCode();
        result = 31 * result + attachments_count.hashCode();
        result = 31 * result + rebounds_count.hashCode();
        result = 31 * result + buckets_count.hashCode();
        result = 31 * result + created_at.hashCode();
        result = 31 * result + updated_at.hashCode();
        result = 31 * result + attachments_url.hashCode();
        result = 31 * result + buckets_url.hashCode();
        result = 31 * result + comments_url.hashCode();
        result = 31 * result + likes_url.hashCode();
        result = 31 * result + projects_url.hashCode();
        result = 31 * result + rebounds_url.hashCode();
        result = 31 * result + animated.hashCode();
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (team != null ? team.hashCode() : 0);
        return result;
    }
}
