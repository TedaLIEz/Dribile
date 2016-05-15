package com.hustunique.jianguo.driclient.ui.fragments;

import android.os.Bundle;

import com.hustunique.jianguo.driclient.models.User;
import com.hustunique.jianguo.driclient.presenters.strategy.GetAllShotsStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.GetShotByIdStrategy;


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
            GetAllShotsStrategy getAllShotsStrategy = new GetAllShotsStrategy();
            mShotListPresenter.setLoadStrategy(getAllShotsStrategy);
            mShotListPresenter.setCacheStrategy(getAllShotsStrategy);
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
