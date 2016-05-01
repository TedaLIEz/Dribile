package com.hustunique.jianguo.driclient.models;

/**
 * Created by JianGuo on 4/21/16.
 */
public class Likes extends BaseBean {
    private String id;
    private String created_at;
    private Shots shot;
    private User user;
    private Team team;

    public String getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public Shots getShot() {
        return shot;
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

        Likes likes = (Likes) o;

        if (!id.equals(likes.id)) return false;
        if (!created_at.equals(likes.created_at)) return false;
        if (!shot.equals(likes.shot)) return false;
        if (!user.equals(likes.user)) return false;
        return team.equals(likes.team);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + created_at.hashCode();
        result = 31 * result + shot.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + team.hashCode();
        return result;
    }
}
