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

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.ui.fragments.BucketListFragment;
import com.hustunique.jianguo.dribile.ui.fragments.IShotFragment;
import com.hustunique.jianguo.dribile.ui.fragments.LikesListFragment;
import com.hustunique.jianguo.dribile.ui.fragments.ShotListFragment;
import com.hustunique.jianguo.dribile.utils.Logger;
import com.squareup.picasso.Picasso;
import com.hustunique.jianguo.mysearchview.MaterialSearchView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final int SETTING = 0x000000;
    private static final int CLEAR_DATA = 0x000001;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.searchView)
    MaterialSearchView mSearchView;

    private ActionBarDrawerToggle mToggle;
    private IShotFragment mContentFragment;
    private FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSetupDrawerContent();
        initFab();
        initSearchView();
    }

    private void initSearchView() {
//        mSearchView.setHint(AppData.getString(R.string.search_hint));
    }


    @OnClick(R.id.fab)
    void initFab() {
        if (mContentFragment != null) {
            mContentFragment.onFabClick();
        }
    }

    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

    private void setSetupDrawerContent() {
        onShotsSelected(false);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        onShotsSelected(false);
                        break;
                    case R.id.nav_settings:
                        onSettingSelected();
                        break;
                    case R.id.nav_buckets:
                        onBucketSelected();
                        break;
                    case R.id.nav_likes:
                        onLikesSelected();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
        View header = View.inflate(this, R.layout.nav_header, null);
        CircleImageView avatar = (CircleImageView) header.findViewById(R.id.auth_user_avatar);
        TextView name = (TextView) header.findViewById(R.id.auth_user_name);
        TextView html = (TextView) header.findViewById(R.id.auth_user_html);

        Picasso.with(this)
                .load(Uri.parse(MyAccountManager.getCurrentUser().getAvatar_url()))
                .placeholder(AppData.getDrawable(R.drawable.avatar_default))
                .into(avatar);
        name.setText(MyAccountManager.getCurrentUser().getName());
        html.setText(MyAccountManager.getCurrentUser().getHtml_url());

        navigationView.addHeaderView(header);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
        drawerLayout.addDrawerListener(mToggle);

    }

    private void onSettingSelected() {
        startActivityForResult(new Intent(this, SettingActivity.class), SETTING);
    }

    private void onLikesSelected() {
        mToolbar.setTitle(AppData.getString(R.string.my_likes));
        mFab.hide();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (null == mFragmentManager.findFragmentByTag(LikesListFragment.class.getName())) {
            mContentFragment = new LikesListFragment();
        }
        fragmentTransaction
                .replace(R.id.container, (Fragment) mContentFragment, LikesListFragment.class.getName());
        fragmentTransaction.commit();
    }

    private void onBucketSelected() {
        mToolbar.setTitle(AppData.getString(R.string.my_buckets));
        mFab.show();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (null == mFragmentManager.findFragmentByTag(BucketListFragment.class.getName())) {
            mContentFragment = BucketListFragment.newInstance();
        }
        fragmentTransaction
                .replace(R.id.container, (Fragment) mContentFragment, BucketListFragment.class.getName());
        fragmentTransaction.commit();
    }


    private void onShotsSelected(boolean reloaded) {
        mToolbar.setTitle(AppData.getString(R.string.shots_title));
        mFab.hide();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (reloaded || null == mFragmentManager.findFragmentByTag(ShotListFragment.class.getName())) {
            mContentFragment = new ShotListFragment();
        }
        fragmentTransaction
                .replace(R.id.container, (Fragment) mContentFragment, ShotListFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        mSearchView.setOnQueryListener(new MaterialSearchView.OnTextQueryListener() {
            @Override
            public boolean onQueryTextSubmit(CharSequence query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(CharSequence newText) {
                mContentFragment.search(newText.toString());
                Logger.d(TAG, "Search: " + newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (!(mContentFragment instanceof ShotListFragment)) {
            onShotsSelected(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTING && resultCode == SETTING) {
            navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
            onShotsSelected(false);
        } else if (requestCode == SETTING && resultCode == CLEAR_DATA) {
            navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
            onShotsSelected(true);
        }
    }
}
