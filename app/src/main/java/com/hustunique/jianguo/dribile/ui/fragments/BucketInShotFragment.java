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

import com.hustunique.jianguo.dribile.models.Buckets;
import com.hustunique.jianguo.dribile.presenters.ShotListPresenter;
import com.hustunique.jianguo.dribile.presenters.strategy.GetShotsFromBucketStrategy;
import com.hustunique.jianguo.dribile.utils.Utils;

/**
 * Created by JianGuo on 4/21/16.
 * Fragment for listing shots in the bucket
 */
public class BucketInShotFragment extends BaseShotListFragment {

    public BucketInShotFragment() {

    }

    public static BucketInShotFragment newInstance(Buckets buckets) {
        BucketInShotFragment bucketInShotFragment = new BucketInShotFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Utils.EXTRA_BUCKET, buckets);
        bucketInShotFragment.setArguments(bundle);
        return bucketInShotFragment;
    }


    @Override
    protected void setStrategy(ShotListPresenter shotListPresenter) {
        if (getArguments() != null) {
            Buckets mBucket = (Buckets) getArguments().getSerializable(Utils.EXTRA_BUCKET);
            shotListPresenter.setLoadStrategy(new GetShotsFromBucketStrategy(mBucket));
        }
    }


}
