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

package com.hustunique.jianguo.dribile.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.ui.fragments.BucketInShotFragment;
import com.hustunique.jianguo.dribile.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BucketDetailActivity extends BaseActivity {
    @BindView(R.id.bucket_detail)
    TextView mDescription;
    @BindView(R.id.bucket_name)
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
        bucket = (Buckets) getIntent().getSerializableExtra(Utils.EXTRA_BUCKET);
        if (bucket == null) {
            throw new NullPointerException("you must give a bucket!");
        }
        initView();
    }

    private void initView() {
        mTitle.setText(bucket.getName());
        mDescription.setText(String.format(AppData.getString(R.string.bucket_description)
                , MyAccountManager.getCurrentUser().getName()
                , bucket.getShots_count()));
        BucketInShotFragment bucketInShotFragment = BucketInShotFragment.newInstance(bucket);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, bucketInShotFragment, null).commit();

    }
}
