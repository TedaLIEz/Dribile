package com.hustunique.jianguo.dribile.ui.fragments;

import android.os.Bundle;

import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.presenters.ShotListPresenter;
import com.hustunique.jianguo.dribile.presenters.strategy.GetAllShotsStrategy;
import com.hustunique.jianguo.dribile.presenters.strategy.GetShotByIdStrategy;
import com.hustunique.jianguo.dribile.utils.Utils;


/**
 * Created by JianGuo on 4/21/16.
 * Basic Fragment for loading shots
 */
public class ShotListFragment extends BaseShotListFragment implements IFabClickFragment {

    public ShotListFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setStrategy(ShotListPresenter shotListPresenter) {
        if (getArguments() != null) {
            User user = (User) getArguments().getSerializable(Utils.EXTRA_USER);
            shotListPresenter.setLoadStrategy(new GetShotByIdStrategy(user));
        } else {
            GetAllShotsStrategy getAllShotsStrategy = new GetAllShotsStrategy();
            shotListPresenter.setLoadStrategy(getAllShotsStrategy);
            shotListPresenter.setCacheStrategy(getAllShotsStrategy);
        }
    }





    public static ShotListFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable(Utils.EXTRA_USER, user);
        ShotListFragment fragment = new ShotListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onFabClick() {

    }



}
