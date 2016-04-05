package com.hustunique.jianguo.driclient.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by JianGuo on 4/5/16.
 * My Floating Button support picasso image loading
 */
public class DesignerFloatingButton extends FloatingActionButton implements Target {

    public DesignerFloatingButton(Context context) {
        super(context);
    }

    public DesignerFloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DesignerFloatingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        setImageDrawable(errorDrawable);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        setImageDrawable(placeHolderDrawable);
    }
}
