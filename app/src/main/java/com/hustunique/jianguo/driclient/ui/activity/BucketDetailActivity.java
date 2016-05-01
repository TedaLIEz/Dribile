package com.hustunique.jianguo.driclient.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.models.Buckets;
import com.hustunique.jianguo.driclient.ui.fragments.BucketShotsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BucketDetailActivity extends BaseActivity {
    public static final String BUCKET = "bucket";
    @Bind(R.id.bucket_detail)
    TextView mDescription;
    @Bind(R.id.bucket_name)
    TextView mTitle;

    private Buckets bucket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        bucket = (Buckets) getIntent().getSerializableExtra(BUCKET);
        if (bucket == null) {
            throw new NullPointerException("you must give a bucket!");
        }
        initView();
    }

    private void initView() {
        mTitle.setText(bucket.getName());
        mDescription.setText(String.format(AppData.getString(R.string.bucket_description)
                , UserManager.getCurrentUser().getUser().getName()
                , bucket.getShots_count()));
        BucketShotsFragment bucketShotsFragment = BucketShotsFragment.newInstance(BucketShotsFragment.SORT_VIEWS, bucket);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, bucketShotsFragment, null).commit();

    }
}
