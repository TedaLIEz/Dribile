
package com.hustunique.jianguo.driclient.bean;

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
