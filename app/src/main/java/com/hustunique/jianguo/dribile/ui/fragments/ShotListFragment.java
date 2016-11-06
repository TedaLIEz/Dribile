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

import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.presenters.ShotListPresenter;
import com.hustunique.jianguo.dribile.presenters.strategy.GetAllShotsStrategy;
import com.hustunique.jianguo.dribile.presenters.strategy.GetShotByIdStrategy;
import com.hustunique.jianguo.dribile.utils.Utils;


/**
 * Created by JianGuo on 4/21/16.
 * Basic Fragment for loading shots
 */
public class ShotListFragment extends BaseShotListFragment implements IFabClickFragment {

    public ShotListFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setStrategy(ShotListPresenter shotListPresenter) {
        if (getArguments() != null) {
            User user = (User) getArguments().getSerializable(Utils.EXTRA_USER);
            shotListPresenter.setLoadStrategy(new GetShotByIdStrategy(user));
        } else {
            GetAllShotsStrategy getAllShotsStrategy = new GetAllShotsStrategy();
            shotListPresenter.setLoadStrategy(getAllShotsStrategy);
            shotListPresenter.setCacheStrategy(getAllShotsStrategy);
        }
    }





    public static ShotListFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable(Utils.EXTRA_USER, user);
        ShotListFragment fragment = new ShotListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onFabClick() {

    }



}
