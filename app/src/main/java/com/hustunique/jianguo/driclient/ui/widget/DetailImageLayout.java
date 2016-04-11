package com.hustunique.jianguo.driclient.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hustunique.jianguo.driclient.utils.CommonUtils;

/**
 * Created by JianGuo on 4/11/16.
 * FrameLayout for full image of a shot, gif is supported.
 */
public class DetailImageLayout extends FrameLayout {

    private Context ctx;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

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
        mImageView = new ImageView(ctx);
        mProgressBar = new ProgressBar(ctx);
        FrameLayout.LayoutParams imageParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        FrameLayout.LayoutParams progressParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressParams.gravity = Gravity.CENTER;
        imageParams.gravity = Gravity.CENTER;
        mImageView.setLayoutParams(imageParams);
        mProgressBar.setLayoutParams(progressParams);
        addView(mImageView);
        addView(mProgressBar);
    }


    public void load(@NonNull String uri) {
        if (TextUtils.isEmpty(uri)) {
            return;
        }
        Log.i("driclient", "loading url " + uri);
        DrawableTypeRequest<Uri> request = Glide.with(ctx).load(Uri.parse(uri));
        if (CommonUtils.isGif(uri)) {
            request.asGif().listener(new RequestListener<Uri, GifDrawable>() {
                @Override
                public boolean onException(Exception e, Uri model, Target<GifDrawable> target, boolean isFirstResource) {
                    Log.wtf("driclient", e);
                    return false;
                }

                @Override
                public boolean onResourceReady(GifDrawable resource, Uri model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    Log.i("driclient", "load gif completed ");
                    mProgressBar.setVisibility(GONE);
                    mImageView.setVisibility(VISIBLE);
                    return false;
                }
            }).into(mImageView);
        } else {
            request.asBitmap().listener(new RequestListener<Uri, Bitmap>() {
                @Override
                public boolean onException(Exception e, Uri model, Target<Bitmap> target, boolean isFirstResource) {
                    Log.wtf("driclient", e);
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Uri model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    Log.i("driclient", "load bitmap completed");
                    mProgressBar.setVisibility(GONE);
                    mImageView.setVisibility(VISIBLE);
                    return false;
                }
            }).into(mImageView);
        }
    }





}
