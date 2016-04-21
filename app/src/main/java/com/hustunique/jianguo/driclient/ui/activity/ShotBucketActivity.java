package com.hustunique.jianguo.driclient.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.bean.Buckets;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleBucketsService;
import com.hustunique.jianguo.driclient.service.DribbbleUserService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.service.factories.ResponseBodyFactory;
import com.hustunique.jianguo.driclient.ui.adapters.BucketsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.AddBucketDialog;
import com.hustunique.jianguo.driclient.ui.widget.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ShotBucketActivity extends BaseActivity {
    @Bind(R.id.rv_buckets)
    RecyclerView mBuckets;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.shots_detail)
    TextView mTextView;

    private Shots mShot;
    private BucketsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_bucket);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mShot = (Shots) getIntent().getSerializableExtra(SHOT);
        if (mShot == null) {
            throw new NullPointerException("Shots mustn't be null in " + getClass().getSimpleName());
        }
        initView();
    }

    private void initView() {
        mAdapter = new BucketsAdapter(this, R.layout.item_buckets);
        mTextView.setText(String.format(AppData.getString(R.string.comments_subtitle)
                , mShot.getTitle()
                , mShot.getUser().getName()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBuckets.setLayoutManager(linearLayoutManager);
        mBuckets.setAdapter(mAdapter);
        mBuckets.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        mAdapter.setOnItemClickListener(new BucketsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, Buckets buckets) {
                addToBucket(buckets);
            }
        });
        mAdapter.setOnItemLongClickListener(new BucketsAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View v, final Buckets buckets) {
                new AlertDialog.Builder(new ContextThemeWrapper(ShotBucketActivity.this, android.R.style.Theme_Holo_Dialog))
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
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBucketDialog dialog = new AddBucketDialog(ShotBucketActivity.this);
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
                        Log.i("driclient", "get name " + name + "get description " + description);
                        createBucket(name, description);
                        dialog.dismiss();
                    }
                });

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
                            showMessage("can't show buckets list " + exception.code());
                        }
                        Log.wtf("driclient", e);
                    }

                    @Override
                    public void onNext(List<Buckets> bucketses) {
                        if (bucketses.size() == 0) {
                            showMessage("Wops! You haven't any buckets yet!");
                        } else {
                            mAdapter.setDataBefore(bucketses);
                        }
                    }
                });
    }

    private void createBucket(String name, String description) {
        ApiServiceFactory.createService(DribbbleBucketsService.class)
                .createBucket(name, description)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Buckets>() {
                    @Override
                    public void onCompleted() {
                        showMessage("add buckets success!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf("driclient", e);
                    }

                    @Override
                    public void onNext(Buckets buckets) {
                        mAdapter.addData(buckets);
                    }
                });
    }

    private void deleteBucket(final Buckets bucket) {
        ResponseBodyFactory.createService(DribbbleBucketsService.class)
                .deleteBucket(bucket.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<retrofit2.Response<ResponseBody>>() {
                    @Override
                    public void call(retrofit2.Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            showMessage("delete bucket " + bucket.getName() + " success");
                            mAdapter.removeData(bucket);
                            finish();
                        } else {
                            Log.e("driclient" ,"delete bucket " + bucket.getId() + " failed");
                        }
                    }
                });
    }

    private void addToBucket(final Buckets bucket) {
        ResponseBodyFactory.createService(DribbbleBucketsService.class)
                .putShotInBucket(bucket.getId(), mShot.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<retrofit2.Response<ResponseBody>>() {
                    @Override
                    public void call(retrofit2.Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.code() == 204) {
                            showMessage("add to bucket " + bucket.getName() + " success");
                            finish();
                        } else {
                            Log.e("driclient", "add to bucket failed " + responseBodyResponse.code());
                        }
                    }
                });
    }

}
