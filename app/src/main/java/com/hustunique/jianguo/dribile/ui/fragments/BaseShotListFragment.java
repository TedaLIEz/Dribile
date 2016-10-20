package com.hustunique.jianguo.dribile.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.PresenterManager;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.presenters.ShotListPresenter;
import com.hustunique.jianguo.dribile.ui.activity.ShotInfoActivity;
import com.hustunique.jianguo.dribile.ui.adapters.ShotsAdapter;
import com.hustunique.jianguo.dribile.ui.viewholders.ShotsViewHolder;
import com.hustunique.jianguo.dribile.ui.widget.PaddingItemDecoration;
import com.hustunique.jianguo.dribile.utils.Utils;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.hustunique.jianguo.dribile.views.ILoadListView;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by JianGuo on 5/6/16.
 * Base Fragment loading shots.
 */
public abstract class BaseShotListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,ILoadListView<Shots> {

    private ShotsAdapter mAdapter;
    private static final String TAG = "BaseShotListFragment";
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

    private ShotListPresenter mShotListPresenter;

    private Unbinder unbinder;
    public BaseShotListFragment() {

    }


    @Override
    public void onResume() {
        super.onResume();
        mShotListPresenter.bindView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mShotListPresenter.unbindView();
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
            mShotListPresenter = new ShotListPresenter();
            setStrategy(mShotListPresenter);
        } else {
            mShotListPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
    }

    protected abstract void setStrategy(ShotListPresenter shotListPresenter);

    private void initSwipeLayout() {
        swipeRefreshLayout.setColorSchemeColors(schemeColor);

        swipeRefreshLayout.setOnRefreshListener(this);
    }


    protected void configureRecyclerView(RecyclerView recyclerView, ShotListPresenter shotListPresenter) {

    }

    private void initRecyclerView() {
        mAdapter = new ShotsAdapter(getActivity(), R.layout.item_shot);
        mAdapter.setOnItemClickListener(new ShotsViewHolder.OnShotClickListener() {
            @Override
            public void onShotClick(Shots model) {
                Utils.startActivityWithShot(BaseShotListFragment.this.getActivity(), ShotInfoActivity.class, model);
            }
        });
        configureRecyclerView(mRecyclerView, mShotListPresenter);
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
                            == mShotListPresenter.getLoadingCount()) {
                        mShotListPresenter.loadMore();
                    }
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @Override
    public void onRefresh() {
        mShotListPresenter.refresh();
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


}
