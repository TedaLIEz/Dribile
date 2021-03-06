/*
 * Copyright (c) 2016.  TedaLIEz <aliezted@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hustunique.jianguo.dribile.models;

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
