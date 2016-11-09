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

package com.hustunique.jianguo.mysearchview;

import android.support.annotation.NonNull;

/**
 * Created by JianGuo on 11/8/16.
 * SearchHistory in {@link SearchAdapter}
 */

public class SearchHistory implements Comparable<SearchHistory> {
    final String keywords;
    int searchTimes;
    long _id;
    public SearchHistory(String keywords) {
        this(keywords, 0);
    }

    public SearchHistory(String keywords, int searchTimes) {
        this.keywords = keywords;
        this.searchTimes = searchTimes;
        this._id = -1;
    }

    public int incrementAndGet() {
        searchTimes++;
        return searchTimes;
    }


    @Override
    public String toString() {
        return "SearchHistory{" +
                "keywords='" + keywords + '\'' +
                ", searchTimes=" + searchTimes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchHistory that = (SearchHistory) o;
        return keywords.equals(that.keywords);
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }

    @Override
    public int compareTo(@NonNull SearchHistory another) {
        return Integer.compare(searchTimes, another.searchTimes);
    }


}
