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

package com.hustunique.jianguo.dribile.service;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.LruCache;

import com.felipecsl.gifimageview.library.GifImageView;
import com.hustunique.jianguo.dribile.service.factories.DribileClientFactory;
import com.hustunique.jianguo.dribile.utils.NetUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by JianGuo on 4/12/16.
 * ImageLoader loading gif in Lrucache
 */
public class GifImageLoader implements ComponentCallbacks2 {
    private GifLruCache cache;
    private int COMPLETE = 0;
    private static Handler mHandler = new Handler();



    public interface Callback {
        void onCompleted(byte[] bytes);

        void onFailed();
    }

    private Callback mCallback;

    public GifImageLoader(Context ctx) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        int maxKb = am.getMemoryClass() * 1024;
        int limitKb = maxKb / 8;
        cache = new GifLruCache(limitKb);
        ctx.registerComponentCallbacks(this);
    }


    public GifImageLoader display(String url) {
        byte[] bytes = cache.get(url);
        if (bytes != null) {
            mCallback.onCompleted(bytes);
        } else {
            doRequest(url);
        }
        return this;
    }

    private void doRequest(String url) {
        OkHttpClient client = DribileClientFactory.createBasicClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCallback != null) {
                            mCallback.onFailed();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final byte[] bytes = response.body().bytes();
                cache.put(response.request().url().toString(), bytes);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCallback != null) {
                            mCallback.onCompleted(bytes);
                        }
                    }
                });

            }
        });

    }

    public void callback(Callback callback) {
        this.mCallback = callback;
    }


    @Override
    public void onTrimMemory(int level) {
        if (level >= TRIM_MEMORY_MODERATE) {
            cache.evictAll();
        } else if (level >= TRIM_MEMORY_BACKGROUND) {
            cache.trimToSize(cache.size() / 2);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }

    private class GifLruCache extends LruCache<String, byte[]> {

        /**
         * @param maxSize for caches that do not override {@link #sizeOf}, this is
         *                the maximum number of entries in the cache. For all other caches,
         *                this is the maximum sum of the sizes of the entries in this cache.
         */
        GifLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, byte[] value) {
            return super.sizeOf(key, value);
        }
    }



    /**
     * Deprecated because we use the singleton okhttpclient in application instead.
     * Check {@link DribileClientFactory} for more information.
     */
    @Deprecated
    private class LoadGifTask extends AsyncTask<String, Void, Integer> {

        private byte[] gifByte;
        private Bitmap bitmap;
        private GifImageView imageView;

        public LoadGifTask(GifImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void onPostExecute(Integer rst) {
            if (rst == COMPLETE) {
                imageView.setBytes(gifByte);
                imageView.startAnimation();
                if (mCallback != null) {
                    mCallback.onCompleted(gifByte);
                }
            } else {
                if (mCallback != null) {
                    mCallback.onFailed();
                }
            }
            super.onPostExecute(rst);
        }

        @Override
        protected Integer doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                gifByte = NetUtils.streamToBytesNio(connection.getInputStream());
                if (gifByte != null) {
                    cache.put(params[0], gifByte);
                }
                return COMPLETE;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 1;
        }


    }
}
