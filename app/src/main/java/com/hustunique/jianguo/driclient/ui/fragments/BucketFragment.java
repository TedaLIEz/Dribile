package com.hustunique.jianguo.driclient.ui.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.bean.Buckets;
import com.hustunique.jianguo.driclient.service.DribbbleBucketsService;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.driclient.ui.activity.BucketDetailActivity;
import com.hustunique.jianguo.driclient.ui.adapters.BucketsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.AddBucketDialog;
import com.hustunique.jianguo.driclient.ui.widget.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Use the {@link BucketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BucketFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    @Bind(R.id.rv_buckets)
    RecyclerView mBuckets;

    //Get rootView used for snackBar
    @Bind(R.id.rootView)
    FrameLayout rootView;
    private BucketsAdapter mAdapter;

    private String mParam1;


    public BucketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onFabClick() {
        AddBucketDialog dialog = new AddBucketDialog(getActivity());
                dialog.show();
                dialog.setOnNegativeButton("No", new AddBucketDialog.OnNegativeButtonListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                dialog.setOnPositiveButton("Yes", new AddBucketDialog.OnPositiveButtonListener() {
                    @Override
                    public void onClick(Dialog dialog, String name, String description) {
                        createBucket(name, description);
                        dialog.dismiss();
                    }
                });
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BucketFragment.
     */
    public static BucketFragment newInstance(String param1) {
        BucketFragment fragment = new BucketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bucket, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mAdapter = new BucketsAdapter(getActivity(), R.layout.item_buckets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mBuckets.setLayoutManager(linearLayoutManager);
        mBuckets.setAdapter(mAdapter);
        mBuckets.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));
        mAdapter.setOnItemClickListener(new BucketsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, Buckets buckets) {
                showShots(buckets);
            }
        });
        mAdapter.setOnItemLongClickListener(new BucketsAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View v, final Buckets buckets) {
                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Dialog))
                        .setTitle("Delete bucket?")
                        .setMessage("Are you sure you want to delete this bucket?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteBucket(buckets);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        ApiServiceFactory.createService(DribbbleUserService.class)
                .getAuthBuckets()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Buckets>>() {
                    @Override
                    public void onCompleted() {
                        Log.i("driclient", "load buckets successfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Snackbar.make(rootView, "Network Exception!", Snackbar.LENGTH_SHORT).show();
                        }
                        Log.wtf("driclient", e);
                    }

                    @Override
                    public void onNext(List<Buckets> bucketses) {
                        if (bucketses.size() == 0) {
                            Snackbar.make(rootView, "Wops! You haven't any buckets yet!", Snackbar.LENGTH_SHORT).show();
                        } else {
                            mAdapter.setDataBefore(bucketses);
                        }
                    }
                });
    }

    private void showShots(Buckets buckets) {
        //TODO: List shots in buckets
        Intent intent = new Intent(getActivity(), BucketDetailActivity.class);
        intent.putExtra(BucketDetailActivity.BUCKET, buckets);
        startActivity(intent);
    }


    private void createBucket(String name, String description) {
        ApiServiceFactory.createService(DribbbleBucketsService.class)
                .createBucket(name, description)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Buckets>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            Snackbar.make(rootView, "Failed to create bucket", Snackbar.LENGTH_SHORT).show();
                        }
                        Log.wtf("driclient", e);

                    }

                    @Override
                    public void onNext(Buckets bucket) {
                        mAdapter.addData(bucket);
                        Snackbar.make(rootView, "Create buckets " + bucket.getName() + " success!", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteBucket(final Buckets bucket) {
        ResponseBodyFactory.createService(DribbbleBucketsService.class)
                .deleteBucket(bucket.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(retrofit2.Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            mAdapter.removeData(bucket);
                            Snackbar.make(rootView, "delete buckets " + bucket.getName() + " success!", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(rootView, "delete buckets " + bucket.getName() + " failed!", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }





}
