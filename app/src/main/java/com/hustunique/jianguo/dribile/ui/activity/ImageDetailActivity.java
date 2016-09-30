package com.hustunique.jianguo.dribile.ui.activity;

import android.os.Bundle;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.models.Shots;
import com.hustunique.jianguo.dribile.ui.widget.DetailImageLayout;
import com.hustunique.jianguo.dribile.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDetailActivity extends BaseActivity {
    @Bind(R.id.image_shot_detail)
    DetailImageLayout mDetailImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        mDetailImageLayout.load((Shots) getIntent().getSerializableExtra(Utils.EXTRA_SHOT));
    }


}
