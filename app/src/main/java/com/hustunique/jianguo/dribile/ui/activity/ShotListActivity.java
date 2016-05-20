package com.hustunique.jianguo.dribile.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.ui.fragments.ShotListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShotListActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    private User mUser;
    public static final String USER = "user";
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
        mUser = (User) getIntent().getSerializableExtra(USER);
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
