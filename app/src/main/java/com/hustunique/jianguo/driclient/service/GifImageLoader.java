package com.hustunique.jianguo.driclient.service;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;

import com.felipecsl.gifimageview.library.GifImageView;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.hustunique.jianguo.driclient.utils.NetUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by JianGuo on 4/12/16.
 * ImageLoader loading gif in Lrucache
 */
public class GifImageLoader implements ComponentCallbacks2 {
    private GifLruCache cache;
    private int COMPLETE = 0;
    public interface Callback {
        void onCompleted();
        void onFailed();
    }
    private Callback mCallback;

    public GifImageLoader(Context ctx) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        int maxKb = am.getMemoryClass() * 1024;
        int limitKb = maxKb / 8;
        cache = new GifLruCache(limitKb);
    }

    public GifImageLoader display(String url, GifImageView imageView) {
        Byte[] bytes = cache.get(url);
        if (bytes != null) {
            imageView.setBytes(convertToByte(bytes));
        } else {
            new LoadGifTask(imageView).execute(url);
        }
        return this;
    }

    public void callback(Callback callback) {
        this.mCallback = callback;
    }

    private Byte[] convertTobyte(byte[] bytes) {
        Byte[] rst = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            rst[i] = bytes[i];
        }
        return rst;
    }

    private byte[] convertToByte(Byte[] bytes) {
        byte[] ret = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            ret[i] = bytes[i];
        }
        return ret;
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

    class GifLruCache extends LruCache<String, Byte[]> {

        /**
         * @param maxSize for caches that do not override {@link #sizeOf}, this is
         *                the maximum number of entries in the cache. For all other caches,
         *                this is the maximum sum of the sizes of the entries in this cache.
         */
        public GifLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Byte[] value) {
            return super.sizeOf(key, value);
        }
    }

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
                    mCallback.onCompleted();
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
                    cache.put(params[0], convertTobyte(gifByte));
                }
                return COMPLETE;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 1;
        }



    }
}
