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
 * POJO for followers
 */
public class Follower extends BaseBean {
    private String id;
    private String created_at;

    private User follower;


    public Follower(){}

    public String getId() {
        return id;
    }


    public String getCreated_at() {
        return created_at;
    }


    public User getFollower() {
        return follower;
    }

}
