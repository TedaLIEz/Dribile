package com.hustunique.jianguo.dribile.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.ShotPresenter;
import com.hustunique.jianguo.dribile.ui.activity.BaseActivity;
import com.hustunique.jianguo.dribile.ui.activity.ShotInfoActivity;
import com.hustunique.jianguo.dribile.ui.viewholders.ShotsViewHolder;


/**
 * Created by JianGuo on 4/4/16.
 * RecyclerView adapter for dribbble shots
 */
public class ShotsAdapter extends MvpRecyclerListAdapter<Shots, ShotPresenter, ShotsViewHolder> {

    private Context mContext;

    @LayoutRes
    int layout;

    public ShotsAdapter(Context context, @LayoutRes int layout) {
        this.layout = layout;
        mContext = context;
    }

    @NonNull
    @Override
    protected ShotPresenter createPresenter(@NonNull Shots model) {
        ShotPresenter shotPresenter = new ShotPresenter();
        shotPresenter.setModel(model);
        return shotPresenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull Shots model) {
        return model.getId();
    }

    @Override
    public ShotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShotsViewHolder shotsViewHolder = new ShotsViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        //// TODO: 4/15/16 Confusing colorFilter when doesn't set this to false
        shotsViewHolder.setIsRecyclable(false);
        shotsViewHolder.setListener(listener);
        return shotsViewHolder;
    }

    private ShotsViewHolder.OnShotClickListener listener;

    public void setOnItemClickListener(ShotsViewHolder.OnShotClickListener listener) {
        this.listener = listener;
    }
}
