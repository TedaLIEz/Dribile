package com.hustunique.jianguo.dribile.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by JianGuo on 3/30/16.
 * Common Network Utils
 */
public class NetUtils {

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            String array[] = s.split("&");
            for (String parameter : array) {
                String v[] = parameter.split("=");
                try {
                    params.putString(URLDecoder.decode(v[0], "UTF-8"),
                            URLDecoder.decode(v[1], "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return params;
    }


    public static Bundle parseUrl(String url) {
        url = url.replace("weiboconnect", "http");
        try {
            URL u = new URL(url);
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }


    /**
     * Whether device is accessible in internet
     * @param ctx Context
     * @return <tt>true</tt> if is available
     */
    public static boolean isNetworkAccessable(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    public static String getNameFromTwitterUrl(String url) {
        if (!url.startsWith("https://twitter.com")) {
            throw new IllegalArgumentException("You are not given the correct twitter profile link");
        }
        int index = url.lastIndexOf("/");
        return url.substring(index + 1, url.length());
    }


    public static byte[] streamToBytesNio(InputStream inputStream) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(8192);
        ReadableByteChannel inChannel = Channels.newChannel(inputStream);
        ByteArrayOutputStream os = new ByteArrayOutputStream(Math.max(32, inputStream.available()));
        WritableByteChannel outChannel = Channels.newChannel(os);
        while (inChannel.read(buffer) != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.compact();
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            outChannel.write(buffer);
        }
        inChannel.close();
        outChannel.close();
        return os.toByteArray();
    }
}
