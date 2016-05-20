package com.hustunique.jianguo.dribile.ui.activity;

import android.os.Bundle;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.ui.widget.DetailImageLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDetailActivity extends BaseActivity {
    public static final String SHARED_SHOTS = "shared_shots";
    @Bind(R.id.image_shot_detail)
    DetailImageLayout mDetailImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        mDetailImageLayout.load((Shots) getIntent().getSerializableExtra(SHARED_SHOTS));
    }


}
