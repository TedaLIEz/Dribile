package com.hustunique.jianguo.dribile.ui.widget;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.felipecsl.gifimageview.library.GifImageView;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.service.GifImageLoader;
import com.hustunique.jianguo.dribile.utils.CommonUtils;
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

    public void load(@NonNull Shots shots) {
        Log.i("driclient", "loading url " + shots.getJson());
        if (CommonUtils.isGif(shots)) {
            new GifImageLoader(ctx).display(shots.getImages().getHidpi() == null ? shots.getImages().getNormal() : shots.getImages().getHidpi(), mGif).callback(new GifImageLoader.Callback() {
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
            Picasso.with(ctx).load(Uri.parse(shots.getImages().getNormal())).into(mGif, new Callback() {
                @Override
                public void onSuccess() {
                    mProgressBar.setVisibility(GONE);
                }

                @Override
                public void onError() {
                    mProgressBar.setVisibility(GONE);
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
