package com.hustunique.jianguo.driclient.ui.widget;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.felipecsl.gifimageview.library.GifImageView;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.GifImageLoader;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        //TODO: simplify code here, merge them into GifImageLoader ?
        Log.i("driclient", "loading url " + images.getJson());
        if (CommonUtils.isGif(images.getNormal())) {
            new GifImageLoader(ctx).display(images.getHidpi(), mGif).callback(new GifImageLoader.Callback() {
                @Override
                public void onCompleted() {
                    mProgressBar.setVisibility(GONE);
                }


                //TODO: Replace with a placeholder
                @Override
                public void onFailed() {
                    mProgressBar.setVisibility(GONE);
                }
            });
        } else {
            Picasso.with(ctx).load(Uri.parse(images.getNormal())).into(mGif, new Callback() {
                @Override
                public void onSuccess() {
                    mProgressBar.setVisibility(GONE);
                }

                @Override
                public void onError() {

                }
            });
        }

    }



    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mGif.isAnimating()) {
            mGif.stopAnimation();
        }
    }
}