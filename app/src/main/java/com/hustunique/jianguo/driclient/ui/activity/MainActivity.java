package com.hustunique.jianguo.driclient.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.ui.fragments.BucketListFragment;
import com.hustunique.jianguo.driclient.ui.fragments.IFabClickFragment;
import com.hustunique.jianguo.driclient.ui.fragments.ShotListFragment;
import com.hustunique.jianguo.driclient.ui.fragments.LikesListFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setSetupDrawerContent();
        initFab();
        initSearchView();
    }

    private void initSearchView() {

        mSearchView.setVoiceSearch(false);
        mSearchView.setEllipsize(true);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        mSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
    }

    private void initFab() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContentFragment != null) {
                    mContentFragment.onFabClick();
                }
            }
        });
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
                    case R.id.nav_user:
                        onUserSelected();
                        break;
                    case R.id.nav_buckets:
                        onBucketSelected();
                        break;
                    case R.id.nav_likes:
                        onLikesSelected();
                        break;
                }
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
        View header = View.inflate(this, R.layout.nav_header, null);
        CircleImageView avatar = (CircleImageView) header.findViewById(R.id.auth_user_avatar);
        TextView name = (TextView) header.findViewById(R.id.auth_user_name);
        TextView html = (TextView) header.findViewById(R.id.auth_user_html);

        Picasso.with(this)
                .load(Uri.parse(UserManager.getCurrentUser().getUser().getAvatar_url()))
                .placeholder(AppData.getDrawable(R.drawable.avatar_default))
                .into(avatar);
        name.setText(UserManager.getCurrentUser().getUser().getName());
        html.setText(UserManager.getCurrentUser().getUser().getHtml_url());

        navigationView.addHeaderView(header);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
        drawerLayout.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void onLikesSelected() {
        mToolbar.setTitle("My likes");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (null == fragmentManager.findFragmentByTag(LikesListFragment.class.getName())) {
            mContentFragment = new LikesListFragment();
        }
        fragmentTransaction
                .replace(R.id.container, (Fragment) mContentFragment, LikesListFragment.class.getName());
        fragmentTransaction.commit();
    }

    private void onBucketSelected() {
        mToolbar.setTitle("My buckets");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (null == fragmentManager.findFragmentByTag(BucketListFragment.class.getName())) {
            mContentFragment = BucketListFragment.newInstance();
        }
        fragmentTransaction
                .replace(R.id.container, (Fragment) mContentFragment, BucketListFragment.class.getName());
        fragmentTransaction.commit();
    }

    private void onUserSelected() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USER, UserManager.getCurrentUser().getUser());
        startActivity(intent);
    }

    private void onShotsSelected() {
        mToolbar.setTitle("Shots");
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (null == fragmentManager.findFragmentByTag(ShotListFragment.class.getName())) {
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
        super.onBackPressed();
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
