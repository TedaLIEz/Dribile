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
 * Created by JianGuo on 3/29/16.
 * POJO for comment in shots
 */
public class Comments extends BaseBean {
    private String id;
    private String body;
    private String likes_count;
    private String links_url;
    private String created_at;
    private String updated_at;

    // who leaves this comment
    private User user;

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public String getLinks_url() {
        return links_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public User getUser() {
        return user;
    }
}
