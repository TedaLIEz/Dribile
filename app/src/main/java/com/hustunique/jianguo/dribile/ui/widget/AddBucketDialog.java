package com.hustunique.jianguo.dribile.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.hustunique.jianguo.dribile.R;
import com.hustunique.jianguo.dribile.app.AppData;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;


/**
 * Created by JianGuo on 4/19/16.
 * AlertDialog for adding a new bucket
 */
public class AddBucketDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.et_name)
    MaterialEditText mName;
    @BindView(R.id.et_description)
    MaterialEditText mDescription;
    @BindView(R.id.bucket_positive)
    Button mPositive;
    @BindView(R.id.bucket_negative)
    Button mNegative;

    public AddBucketDialog(Context context) {
        this(context, 0);
    }

    public AddBucketDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_bucket);
        mNegative = (Button) findViewById(R.id.bucket_negative);
        mPositive = (Button) findViewById(R.id.bucket_positive);
        mDescription = (MaterialEditText) findViewById(R.id.et_description);
        mName = (MaterialEditText) findViewById(R.id.et_name);
        mNegative.setOnClickListener(this);
        mPositive.setOnClickListener(this);

    }


    public interface OnPositiveButtonListener {
        void onClick(Dialog dialog, String name, String description);
    }

    public interface OnNegativeButtonListener {
        void onClick(Dialog dialog);
    }
    private OnPositiveButtonListener onPositiveButtonListener;
    private OnNegativeButtonListener onNegativeButtonListener;

    public void setOnPositiveButton(String text, OnPositiveButtonListener onPositiveButtonListener) {
        mPositive.setText(text);
        this.onPositiveButtonListener = onPositiveButtonListener;
    }


    public void setOnNegativeButton(String text, OnNegativeButtonListener onNegativeButtonListener) {
        mNegative.setText(text);
        this.onNegativeButtonListener = onNegativeButtonListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bucket_negative:
                if (onNegativeButtonListener != null) {
                    onNegativeButtonListener.onClick(this);
                }
                break;
            case R.id.bucket_positive:
                if (onPositiveButtonListener != null) {
                    if (mName.getText().length() == 0) {
                        mName.setError(AppData.getString(R.string.bucket_dialog_error));
                        break;
                    }
                    onPositiveButtonListener.onClick(this, mName.getText().toString(), mDescription.getText().toString());
                }
                break;
        }
    }
}
