package com.hustunique.jianguo.driclient.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.ui.viewholders.BaseViewHolder;
import com.hustunique.jianguo.driclient.ui.viewholders.ShotsViewHolder;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * Created by JianGuo on 4/4/16.
 * RecyclerView adapter for dribbble shots
 */
public class ShotsAdapter extends BaseDriListAdapter<Shots> {

    private Context mContext;

    @LayoutRes
    int layout;


    public interface OnItemClickListener {
        void onClick(View v, Shots shots);
    }

    private OnItemClickListener onItemClickListener;


    public ShotsAdapter() {
        throw new IllegalArgumentException("You must give a context!");
    }

    public ShotsAdapter(Context ctx, @LayoutRes int layout) {
        mContext = ctx;
        this.layout = layout;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layout, parent, false);
        return new ShotsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        ShotsViewHolder viewHolder = (ShotsViewHolder) holder;
        Shots shots = getItem(position);
        viewHolder.setData(shots);
        //// FIXME: 4/15/16 Confusing colorFilter when doesn't set this to false
        viewHolder.setIsRecyclable(false);
        viewHolder.mCommentCount.setText(shots.getComments_count());
        viewHolder.mLikeCount.setText(shots.getLikes_count());
        viewHolder.mViewCount.setText(shots.getViews_count());
        String animated = shots.getAnimated();
        if (animated.equals("true")) {
            viewHolder.mGif.setVisibility(View.VISIBLE);
            viewHolder.mImage.setColorFilter(CommonUtils.brightIt(-100));
        } else {
            viewHolder.mGif.setVisibility(View.INVISIBLE);
        }
        Picasso.with(mContext)
                .load(shots.getImages().getNormal())
                .placeholder(AppData.getDrawable(R.drawable.shots_default))
                .into(viewHolder.mImage);

        viewHolder.mImage.setTag(shots);
        Picasso.with(mContext)
                .load(shots.getUser().getAvatar_url())
                .placeholder(AppData.getDrawable(R.drawable.avatar_default))
                .into(viewHolder.mAvatar);
        viewHolder.mTitle.setText(shots.getTitle());

        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, getItem(holder.getAdapterPosition()));
                }
            }
        });


        super.onBindViewHolder(holder, position);

    }


}
