package com.hustunique.jianguo.driclient.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.bean.User;
import com.hustunique.jianguo.driclient.ui.fragments.BaseShotListFragment;
import com.hustunique.jianguo.driclient.ui.fragments.ShotListFragment;

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
        mUser = (User) getIntent().getSerializableExtra(USER);
        getSupportActionBar().setTitle(String.format(AppData.getString(R.string.user_shots), mUser.getName()));
        if (mUser == null) throw new NullPointerException("must give a user!");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,
                        ShotListFragment.newInstance(BaseShotListFragment.SORT_VIEWS
                                , mUser.getId()))
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
