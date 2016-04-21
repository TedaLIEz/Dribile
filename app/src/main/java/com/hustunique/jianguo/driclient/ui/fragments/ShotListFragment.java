package com.hustunique.jianguo.driclient.ui.fragments;

import android.os.Bundle;
import android.util.Log;

import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 4/21/16.
 * Fragment for loading all shots in dribbble
 */
public class ShotListFragment extends BaseShotListFragment {



    public ShotListFragment() {

    }

    public static ShotListFragment newInstance(@SortType String sortType) {
        ShotListFragment shotListFragment = new ShotListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ShotListFragment.ARG_SORT_TYPE, sortType);
        shotListFragment.setArguments(bundle);
        return shotListFragment;
    }

    @Override
    public Observable<List<Shots>> loadData(Map<String, String> params) {
        return ApiServiceFactory.createService(DribbbleShotsService.class)
                .getAllShots(params);
    }


    @Override
    public void onFabClick() {
        Log.i("driclient", "add new shots");
    }
}
