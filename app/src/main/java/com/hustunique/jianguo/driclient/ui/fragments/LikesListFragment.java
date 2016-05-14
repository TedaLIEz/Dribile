package com.hustunique.jianguo.driclient.ui.fragments;

import android.os.Bundle;

import com.hustunique.jianguo.driclient.presenters.strategy.GetLikesByIdStrategy;
import com.hustunique.jianguo.driclient.presenters.strategy.GetMyLikesStrategy;


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
            GetMyLikesStrategy getMyLikesStrategy = new GetMyLikesStrategy();
            mShotListPresenter.setLoadStrategy(getMyLikesStrategy);
            mShotListPresenter.setCacheStrategy(getMyLikesStrategy);
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
