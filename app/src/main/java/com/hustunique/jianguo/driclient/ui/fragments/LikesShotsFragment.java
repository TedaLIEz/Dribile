package com.hustunique.jianguo.driclient.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hustunique.jianguo.driclient.models.Likes;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by JianGuo on 4/21/16.
 * Fragment for listing shots in likes
 */
public class LikesShotsFragment extends BaseShotListFragment {
    public static final String ID = "id";
    private String id;
    public static LikesShotsFragment newInstance(@SortType String sortType, @Nullable String id) {
        Bundle args = new Bundle();
        args.putString(ARG_SORT_TYPE, sortType);
        args.putString(ID, id);
        LikesShotsFragment fragment = new LikesShotsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString(ID);
    }

    @Override
    public Observable<List<Shots>> loadData(Map<String, String> params) {
        if (id == null) {
            return ApiServiceFactory.createService(DribbbleUserService.class)
                    .getAuthLikeShots()
                    .map(new Func1<List<Likes>, List<Shots>>() {
                        @Override
                        public List<Shots> call(List<Likes> likes) {
                            List<Shots> rst = new ArrayList<Shots>();
                            for (Likes like : likes) {
                                rst.add(like.getShot());
                            }
                            return rst;
                        }
                    });
        } else {
            return ApiServiceFactory.createService(DribbbleUserService.class)
                    .getLikeShots(id)
                    .map(new Func1<List<Likes>, List<Shots>>() {
                        @Override
                        public List<Shots> call(List<Likes> likes) {
                            List<Shots> rst = new ArrayList<Shots>();
                            for (Likes like : likes) {
                                rst.add(like.getShot());
                            }
                            return rst;
                        }
                    });
        }

    }

    @Override
    public void onFabClick() {

    }
}
