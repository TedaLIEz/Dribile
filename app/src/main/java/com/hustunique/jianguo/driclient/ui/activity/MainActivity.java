package com.hustunique.jianguo.driclient.ui.activity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.ui.fragments.BaseFragment;
import com.hustunique.jianguo.driclient.ui.fragments.ShotsFragment;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.container)
    FrameLayout container;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private ActionBarDrawerToggle mToggle;
    private BaseFragment mContentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (UserManager.getCurrentUser() != null) {
            Log.i("driclient", "find login user " + UserManager.getCurrentUser().getJson());
        }

        setSetupDrawerContent();
    }

    private void setSetupDrawerContent() {
        onShotsSelected();
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

    private void onUserSelected() {

    }

    private void onShotsSelected() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (null == fragmentManager.findFragmentByTag(ShotsFragment.class.getName())) {
            mContentFragment = ShotsFragment.newInstance(ShotsFragment.SORT_VIEWS);
        }
        fragmentTransaction
                .replace(R.id.container, mContentFragment, ShotsFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
