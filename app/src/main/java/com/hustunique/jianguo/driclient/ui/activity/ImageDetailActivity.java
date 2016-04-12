package com.hustunique.jianguo.driclient.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.ui.widget.DetailImageLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDetailActivity extends BaseActivity {
    public static final String SHARED_IMAGE = "shared_image";
    @Bind(R.id.image_shot_detail)
    DetailImageLayout mDetailImageLayout;

    //// FIXME: 4/11/16 turn statusBar color to black
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        mDetailImageLayout.load((Shots.Images) getIntent().getSerializableExtra(SHARED_IMAGE));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
