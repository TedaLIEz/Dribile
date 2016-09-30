package com.hustunique.jianguo.dribile.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.hustunique.jianguo.dribile.models.User;
import com.hustunique.jianguo.dribile.ui.fragments.LikesListFragment;
import com.hustunique.jianguo.dribile.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LikeListActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(AppData.getDrawable(R.drawable.ic_close_white_24dp));
        mUser = (User) getIntent().getSerializableExtra(Utils.EXTRA_USER);
        getSupportActionBar().setTitle(String.format(AppData.getString(R.string.user_likes), mUser.getName()));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,
                        LikesListFragment.newInstance(mUser.getId()))
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
