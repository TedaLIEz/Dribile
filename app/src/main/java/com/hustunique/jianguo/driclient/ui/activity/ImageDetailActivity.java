package com.hustunique.jianguo.driclient.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.ui.widget.DetailImageLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDetailActivity extends BaseActivity {
    public static final String SHARED_SHOTS = "shared_shots";
    @Bind(R.id.image_shot_detail)
    DetailImageLayout mDetailImageLayout;

    //// FIXME: 4/11/16 turn statusBar color to black
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Use this to turn ui full screen, but somehow ruin the shared element animation
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_image_detail);

        ButterKnife.bind(this);
        mDetailImageLayout.load((Shots) getIntent().getSerializableExtra(SHARED_SHOTS));
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
