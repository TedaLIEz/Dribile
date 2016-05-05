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
 * Basic Fragment for loading shots
 */
public class LikesListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ILoadListView<Shots>, IFabClickFragment {
    public static final String ID = "id";
    private ShotsAdapter mAdapter;

    private GridLayoutManager mGridLayoutManager;

    @BindColor(R.color.colorAccent)
    int schemeColor;

    @BindDimen(R.dimen.item_divider_size)
    int dividerSize;

    @Bind(R.id.layout_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.rv_shots)
    RecyclerView mRecyclerView;

    @Bind(R.id.loading)
    ProgressBar mProgress;


    private ShotListPresenter mShotListPresenter;


    public LikesListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mShotListPresenter = new ShotListPresenter();
            if (getArguments() != null) {
                String id = getArguments().getString(ID);
                mShotListPresenter.setLoadStrategy(new GetLikesByIdStrategy(id));
            } else {
                mShotListPresenter.setLoadStrategy(new GetMyLikesStrategy());
            }
        } else {
            mShotListPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
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
    public void onResume() {
        super.onResume();
        mShotListPresenter.bindView(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mShotListPresenter, outState);
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
        ButterKnife.bind(this, view);
        mProgress.setPadding(0, 0, 0, CommonUtils.getTransparentNavigationBarHeight(getActivity()));
        initSwipeLayout();
        initRecyclerView();
        return view;
    }


    private void initSwipeLayout() {
        swipeRefreshLayout.setColorSchemeColors(schemeColor);

        swipeRefreshLayout.setOnRefreshListener(this);
    }


    private void initRecyclerView() {
        mAdapter = new ShotsAdapter(getActivity(), R.layout.item_shot);
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
                        + CommonUtils.getTransparentNavigationBarHeight(getActivity()));
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
        ButterKnife.unbind(this);
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
        Log.wtf("driclient", e);
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

    @Override
    public void onFabClick() {

    }

}
