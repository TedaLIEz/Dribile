package com.hustunique.jianguo.driclient.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.ui.widget.DetailImageLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDetailActivity extends BaseActivity {
    public static final String SHARED_URI = "shared_uri";
    @Bind(R.id.image_shot_detail)
    DetailImageLayout mDetailImageLayout;
    //// FIXME: 4/11/16 turn statusBar color to black
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        mDetailImageLayout.load(getIntent().getStringExtra(SHARED_URI));
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
