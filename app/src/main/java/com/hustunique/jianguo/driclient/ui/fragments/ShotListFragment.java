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
import com.hustunique.jianguo.driclient.models.User;
import com.hustunique.jianguo.driclient.presenters.ShotListPresenter;
import com.hustunique.jianguo.driclient.presenters.strategy.GetShotByIdStrategy;
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
 * Basic Fragment for loading shots
 */
public class ShotListFragment extends BaseShotListFragment implements IFabClickFragment {
    public static final String USER = "user";
    public ShotListFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setStrategy() {
        if (getArguments() != null) {
            User user = (User) getArguments().getSerializable(USER);
            mShotListPresenter.setLoadStrategy(new GetShotByIdStrategy(user));
        } else {
            mShotListPresenter.setCached();
        }
    }

    public static ShotListFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable(USER, user);
        ShotListFragment fragment = new ShotListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onFabClick() {

    }

}
