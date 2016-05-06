package com.hustunique.jianguo.driclient.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.PresenterManager;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.presenters.ShotListPresenter;
import com.hustunique.jianguo.driclient.presenters.strategy.GetLikesByIdStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.GetMyLikesStrategy;
import com.hustunique.jianguo.driclient.ui.adapters.ShotsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.PaddingItemDecoration;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.hustunique.jianguo.driclient.views.ILoadListView;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


/**
 * Created by JianGuo on 4/21/16.
 * Fragment for loading shots in a user's likes, if you don't give a user id it will load the auth
 * user's likes by default.
 */
public class LikesListFragment extends BaseShotListFragment implements IFabClickFragment {
    public static final String ID = "id";


    public LikesListFragment() {
        // Required empty public constructor
    }



    @Override
    protected void setStrategy() {
        if (getArguments() != null) {
            String id = getArguments().getString(ID);
            mShotListPresenter.setLoadStrategy(new GetLikesByIdStrategy(id));
        } else {
            mShotListPresenter.setLoadStrategy(new GetMyLikesStrategy());
        }
    }

    public static LikesListFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(ID, id);
        LikesListFragment fragment = new LikesListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onFabClick() {

    }

}
