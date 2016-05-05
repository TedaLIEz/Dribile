package com.hustunique.jianguo.driclient.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.presenters.ShotPresenter;
import com.hustunique.jianguo.driclient.ui.activity.ShotInfoActivity;
import com.hustunique.jianguo.driclient.ui.viewholders.BaseViewHolder;
import com.hustunique.jianguo.driclient.ui.viewholders.ShotsViewHolder;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


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
        shotsViewHolder.setListener(new ShotsViewHolder.OnShotClickListener() {
            @Override
            public void onShotClick(Shots model) {
                Log.e("driclient", "click on shots ");
                Intent intent = new Intent(mContext, ShotInfoActivity.class);
                intent.putExtra("shots", model);
                mContext.startActivity(intent);
            }
        });
        return shotsViewHolder;
    }
}
