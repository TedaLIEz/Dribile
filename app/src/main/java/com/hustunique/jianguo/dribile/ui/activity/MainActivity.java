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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.am.MyAccountManager;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.ui.fragments.BucketListFragment;
import com.hustunique.jianguo.dribile.ui.fragments.IFabClickFragment;
import com.hustunique.jianguo.dribile.ui.fragments.LikesListFragment;
import com.hustunique.jianguo.dribile.ui.fragments.ShotListFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    private static final int SETTING = 0x000000;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.searchView)
    MaterialSearchView mSearchView;

    private ActionBarDrawerToggle mToggle;
    private IFabClickFragment mContentFragment;
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

        mSearchView.setVoiceSearch(false);
        mSearchView.setHintTextColor(R.color.grey20_color);
        mSearchView.setHint("Search");
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                mFab.hide();
            }

            @Override
            public void onSearchViewClosed() {
                mFab.show();
            }
        });
        mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
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
    //TODO: find icons for buckets!
    private void setSetupDrawerContent() {
        onShotsSelected();
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        onShotsSelected();
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
                .load(Uri.parse(MyAccountManager.getCurrentUser().getUser().getAvatar_url()))
                .placeholder(AppData.getDrawable(R.drawable.avatar_default))
                .into(avatar);
        name.setText(MyAccountManager.getCurrentUser().getUser().getName());
        html.setText(MyAccountManager.getCurrentUser().getUser().getHtml_url());

        navigationView.addHeaderView(header);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
        drawerLayout.addDrawerListener(mToggle);

    }

    private void onSettingSelected() {
        startActivityForResult(new Intent(this, SettingActivity.class), SETTING);
    }

    private void onLikesSelected() {
        mToolbar.setTitle("My likes");
        mFab.show();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (null == mFragmentManager.findFragmentByTag(LikesListFragment.class.getName())) {
            mContentFragment = new LikesListFragment();
        }
        fragmentTransaction
                .replace(R.id.container, (Fragment) mContentFragment, LikesListFragment.class.getName());
        fragmentTransaction.commit();
    }

    private void onBucketSelected() {
        mToolbar.setTitle("My buckets");
        mFab.show();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (null == mFragmentManager.findFragmentByTag(BucketListFragment.class.getName())) {
            mContentFragment = BucketListFragment.newInstance();
        }
        fragmentTransaction
                .replace(R.id.container, (Fragment) mContentFragment, BucketListFragment.class.getName());
        fragmentTransaction.commit();
    }


    private void onShotsSelected() {
        mToolbar.setTitle("Shots");
        mFab.hide();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (null == mFragmentManager.findFragmentByTag(ShotListFragment.class.getName())) {
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
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mSearchView.showSearch();
                mSearchView.setVisibility(View.VISIBLE);
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
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTING && resultCode == SETTING) {
            navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
            onShotsSelected();
        }
    }
}
