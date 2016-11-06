/*
 * Copyright (c) 2016.  TedaLIEz <aliezted@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hustunique.jianguo.dribile.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.app.PresenterManager;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.LikeListPresenter;
import com.hustunique.jianguo.dribile.presenters.strategy.GetLikesByIdStrategy;
import com.hustunique.jianguo.dribile.presenters.strategy.GetMyLikesStrategy;
import com.hustunique.jianguo.dribile.ui.activity.ShotInfoActivity;
import com.hustunique.jianguo.dribile.ui.adapters.ShotsAdapter;
import com.hustunique.jianguo.dribile.ui.viewholders.ShotsViewHolder;
import com.hustunique.jianguo.dribile.ui.widget.PaddingItemDecoration;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.views.LikeListView;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


/**
 * Created by JianGuo on 4/21/16.
 * Fragment for loading shots in a user's likes, if you don't give a user id it will load the auth
 * user's likes by default.
 */
public class LikesListFragment extends BaseFragment implements LikeListView, IFabClickFragment, SwipeRefreshLayout.OnRefreshListener {
    public static final String ID = "id";
    public static final String TAG = "LikesListFragment";
    private LikeListPresenter mLikeListPresenter;
    private Unbinder unbinder;

    private ShotsAdapter mAdapter;

    private GridLayoutManager mGridLayoutManager;

    @BindColor(R.color.colorAccent)
    int schemeColor;

    @BindDimen(R.dimen.item_divider_size)
    int dividerSize;

    @BindView(R.id.layout_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rv_shots)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading)
    ProgressBar mProgress;

    public LikesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shots, container, false);
        unbinder = ButterKnife.bind(this, view);
        mProgress.setPadding(0, 0, 0, Utils.getTransparentNavigationBarHeight(getActivity()));
        initSwipeLayout();
        initRecyclerView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (getArguments() != null) {
                String id = getArguments().getString(ID);
                mLikeListPresenter = new LikeListPresenter();
                mLikeListPresenter.setLoadStrategy(new GetLikesByIdStrategy(id));
            } else {
                mLikeListPresenter = new LikeListPresenter();
                GetMyLikesStrategy getMyLikesStrategy = new GetMyLikesStrategy();
                mLikeListPresenter.setLoadStrategy(getMyLikesStrategy);
                mLikeListPresenter.setCacheStrategy(getMyLikesStrategy);
            }
        } else {
            mLikeListPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mLikeListPresenter.bindView(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mLikeListPresenter, outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLikeListPresenter.unbindView();
        unbinder.unbind();
    }

    private void initSwipeLayout() {
        swipeRefreshLayout.setColorSchemeColors(schemeColor);

        swipeRefreshLayout.setOnRefreshListener(this);
    }


    protected void configureRecyclerView(RecyclerView recyclerView, final LikeListPresenter likeListPresenter) {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                likeListPresenter.removeChild(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initRecyclerView() {
        mAdapter = new ShotsAdapter(getActivity(), R.layout.item_shot);
        mAdapter.setOnItemClickListener(new ShotsViewHolder.OnShotClickListener() {
            @Override
            public void onShotClick(Shots model) {
                Utils.startActivityWithShot(getActivity(), ShotInfoActivity.class, model);
            }
        });
        configureRecyclerView(mRecyclerView, mLikeListPresenter);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(mAdapter));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new PaddingItemDecoration(dividerSize));
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setPadding(
                mRecyclerView.getPaddingLeft(),
                mRecyclerView.getPaddingTop(),
                mRecyclerView.getPaddingRight(),
                mRecyclerView.getPaddingBottom()
                        + Utils.getTransparentNavigationBarHeight(getActivity()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean loading = true;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mRecyclerView.getChildCount();
                int firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();
                if (dy > 0) {
                    if (loading) {
                        loading = false;
                    }
                    if (!loading && (visibleItemCount + firstVisibleItem)
                            == mLikeListPresenter.getLoadingCount()) {
                        mLikeListPresenter.loadMore();
                    }
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    @Override
    public void onRefresh() {
        mLikeListPresenter.refresh();
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showLoading() {
        // Show animation first time.
        // See http://stackoverflow.com/a/26910973/4380801
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onError(Throwable e) {
        Logger.wtf(TAG, e);
    }

    @Override
    public void showData(List<Shots> shots) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        mProgress.setVisibility(View.GONE);
        mAdapter.clearAndAddAll(shots);
    }

    @Override
    public void showLoadingMore() {
        mProgress.setVisibility(View.VISIBLE);
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

    @Override
    public void unlikeShot(final int pos) {
        mAdapter.removeItem(pos);
    }

    @Override
    public void restoreShot(int pos, Shots removeShot) {
        mAdapter.addItem(removeShot, pos);
        if (pos == 0) {
            mRecyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void showUndo(final int pos) {
        Snackbar snackbar = Snackbar
                .make(mRecyclerView, AppData.getString(R.string.unlike_shots), Snackbar.LENGTH_LONG)
                .setAction(AppData.getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mLikeListPresenter.restoreShot(pos);
                        Log.i(TAG, "undo with pos " + pos);
                    }
                });
        snackbar.show();
    }
}
