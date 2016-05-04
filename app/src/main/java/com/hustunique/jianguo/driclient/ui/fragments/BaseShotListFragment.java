package com.hustunique.jianguo.driclient.ui.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringDef;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.models.Shots;
import com.hustunique.jianguo.driclient.dao.ShotsDataHelper;
import com.hustunique.jianguo.driclient.ui.activity.ShotInfoActivity;
import com.hustunique.jianguo.driclient.ui.adapters.ShotsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.PaddingItemDecoration;
import com.hustunique.jianguo.driclient.utils.CommonUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by JianGuo on 4/21/16.
 * Basic Fragment for loading shots
 */
public abstract class BaseShotListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    // show shots per page
    private final int COUNT_PER_PAGE = 20;
    private String mSortType;

    //loading page of shots
    private int page;

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

    private boolean refreshFromTop;
    protected List<Shots> data = new ArrayList<>();

    @Override
    public void onRefresh() {
        page = 1;
        refreshFromTop = true;
        loadData(page);
    }


    protected static final String ARG_SORT_TYPE = "sort";
    //Sort type
    public static final String SORT_COMMENTS = "comments";
    public static final String SORT_RECENT = "recent";
    public static final String SORT_VIEWS = "views";

    @StringDef({SORT_COMMENTS, SORT_RECENT, SORT_VIEWS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortType {
    }

    public BaseShotListFragment() {
        // Required empty public constructor
    }


    public abstract Observable<List<Shots>> loadData(Map<String, String> params);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSortType = getArguments().getString(ARG_SORT_TYPE);
        }
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
            int visibleItemCount;
            int totalItemCount;
            int firstVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mGridLayoutManager.getItemCount();
                firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();
                if (dy > 0) {
                    if (loading) {
                        loading = false;
                    }
                    if (!loading && (visibleItemCount + firstVisibleItem)
                            == page * COUNT_PER_PAGE) {
                        mProgress.setVisibility(View.VISIBLE);
                        loadNextPage();
                        loading = true;
                    }
                }
            }
        });
    }

    private void initSwipeLayout() {
        swipeRefreshLayout.setColorSchemeColors(schemeColor);
        // Show animation first time.
        // See http://stackoverflow.com/a/26910973/4380801
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    // Load data from bottom
    private void loadNextPage() {
        refreshFromTop = false;
        page++;
        Log.i("driclient", this.getTag() + " get more data from page " + page);
        loadData(page);
    }

    private void loadData(int page) {
        if (refreshFromTop) {
            swipeRefreshLayout.setRefreshing(true);
        }
        Log.i("driclient", this.getTag() + " load data in " + page);
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put(ARG_SORT_TYPE, mSortType);
        params.put("per_page", String.valueOf(COUNT_PER_PAGE));
        loadData(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shots>>() {
                    @Override
                    public void onCompleted() {
                        mProgress.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf("driclient", e);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<Shots> shotses) {
                        data = shotses;
                        final ShotsDataHelper helper = new ShotsDataHelper();
                        if (refreshFromTop) {
                            mAdapter.clearAndAddAll(data);
                            mRecyclerView.smoothScrollToPosition(0);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Looper.prepare();
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            helper.deleteAll();
                                            helper.bulkInsert(data);
                                        }
                                    });
                                }
                            }).start();
                        } else {
                            mAdapter.addAll(data);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Looper.prepare();
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            helper.bulkInsert(data);
                                        }
                                    });
                                }
                            }).start();
                        }
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadFromDB() {
        Gson gson = new Gson();
        ShotsDataHelper helper = new ShotsDataHelper();
        Cursor cursor = helper.getList();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            data.add(gson.fromJson(
                    cursor.getString(cursor.getColumnIndex(ShotsDataHelper.ShotsTable.JSON)),
                    Shots.class));
        }
        mAdapter.addAll(data);
    }


}
