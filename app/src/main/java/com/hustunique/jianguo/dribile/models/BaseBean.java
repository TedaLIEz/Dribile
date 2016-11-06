
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

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * Created by Dsnc on 1/9/15.
 * Basic pojo for driclient
 */
public abstract class BaseBean implements Serializable {

    /**
     * Get information in json format
     * @return the json string
     */
    public String getJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static class DriclientExclusionStrategy implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return clazz == CharSequence.class;
        }
    }
}
