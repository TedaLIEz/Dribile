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
 * Created by JianGuo on 4/7/16.
 */
public class Attachment extends BaseBean {
    private String id;
    private String url;
    private String thumbnail_url;
    private String size;
    private String content_type;
    private String views_count;
    private String created_at;

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public String getSize() {
        return size;
    }

    public String getContent_type() {
        return content_type;
    }

    public String getViews_count() {
        return views_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attachment that = (Attachment) o;

        if (!id.equals(that.id)) return false;
        if (!url.equals(that.url)) return false;
        if (!thumbnail_url.equals(that.thumbnail_url)) return false;
        if (!size.equals(that.size)) return false;
        if (!content_type.equals(that.content_type)) return false;
        if (!views_count.equals(that.views_count)) return false;
        return created_at.equals(that.created_at);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + thumbnail_url.hashCode();
        result = 31 * result + size.hashCode();
        result = 31 * result + content_type.hashCode();
        result = 31 * result + views_count.hashCode();
        result = 31 * result + created_at.hashCode();
        return result;
    }
}
