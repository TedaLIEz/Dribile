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
import android.view.MenuItem;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.ui.fragments.ShotListFragment;
import com.hustunique.jianguo.dribile.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShotListActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(AppData.getDrawable(R.drawable.ic_close_white_24dp));
        mUser = (User) getIntent().getSerializableExtra(Utils.EXTRA_USER);
        getSupportActionBar().setTitle(String.format(AppData.getString(R.string.user_shots), mUser.getName()));
        if (mUser == null) throw new NullPointerException("must give a user!");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,
                        ShotListFragment.newInstance(mUser))
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
