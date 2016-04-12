package com.hustunique.jianguo.driclient.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.felipecsl.gifimageview.library.GifImageView;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JianGuo on 4/11/16.
 * FrameLayout for full image of a shot, gif is supported.
 */
public class DetailImageLayout extends FrameLayout {

    private Context ctx;
    private ProgressBar mProgressBar;
    private GifImageView mGif;

    public DetailImageLayout(Context context) {
        this(context, null);
    }

    public DetailImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetailImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctx = context;
        initView();
    }

    private void initView() {
        mGif = new GifImageView(ctx);
        mProgressBar = new ProgressBar(ctx);
        FrameLayout.LayoutParams imageParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        FrameLayout.LayoutParams progressParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressParams.gravity = Gravity.CENTER;
        imageParams.gravity = Gravity.CENTER;
        mGif.setLayoutParams(imageParams);
        mProgressBar.setLayoutParams(progressParams);
        mGif.setFitsSystemWindows(true);
        addView(mGif);
        addView(mProgressBar);
    }

    public void load(@NonNull Shots.Images images) {
        Log.i("driclient", "loading url " + images.getJson());
        new LoadGifTask(images, CommonUtils.isGif(images.getNormal())).execute();

    }


    private class LoadGifTask extends AsyncTask<Void, Void, Void> {

        private String gifAddr;
        private byte[] gifByte;
        private boolean isGif;
        private Bitmap bitmap;
        public LoadGifTask(Shots.Images images, boolean isGif) {
            if (isGif) {
                this.gifAddr = images.getHidpi();
            } else {
                this.gifAddr = images.getNormal();
            }
            this.isGif = isGif;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (isGif) {
                mGif.setBytes(gifByte);
                mGif.startAnimation();
            } else {
                mGif.setImageBitmap(bitmap);
            }
            mProgressBar.setVisibility(GONE);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(gifAddr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                if (isGif) {
                    gifByte = streamToBytes(connection.getInputStream());
                } else {
                    bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private byte[] streamToBytes(InputStream inputStream) {
            ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int len;
            try {
               while ((len = inputStream.read(buffer)) >= 0) {
                    os.write(buffer, 0, len);
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return os.toByteArray();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mGif.isAnimating()) {
            mGif.stopAnimation();
        }
    }
}
