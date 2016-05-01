package com.hustunique.jianguo.driclient.ui.fragments;

import android.os.Bundle;
import android.util.Log;

import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by JianGuo on 4/21/16.
 * Fragment for loading shots in dribbble
 */
public class ShotListFragment extends BaseShotListFragment {

    public static final String ID = "id";
    private String id;
    public ShotListFragment() {

    }

    public static ShotListFragment newInstance(@SortType String sortType, String id) {
        ShotListFragment shotListFragment = new ShotListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ShotListFragment.ARG_SORT_TYPE, sortType);
        bundle.putString(ID, id);
        shotListFragment.setArguments(bundle);
        return shotListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString(ID);
    }

    @Override
    public Observable<List<Shots>> loadData(Map<String, String> params) {
        if (id == null) {
            return ApiServiceFactory.createService(DribbbleShotsService.class)
                    .getAllShots(params);
        } else {
            return ApiServiceFactory.createService(DribbbleUserService.class)
                    .getShots(id, params);
        }

    }


    @Override
    public void onFabClick() {
        Log.i("driclient", "add new shots");
    }
}
