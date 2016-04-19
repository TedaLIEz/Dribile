package com.hustunique.jianguo.driclient.ui.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hustunique.jianguo.driclient.R;
import com.hustunique.jianguo.driclient.app.AppData;
import com.hustunique.jianguo.driclient.app.UserManager;
import com.hustunique.jianguo.driclient.bean.Comments;
import com.hustunique.jianguo.driclient.bean.Shots;
import com.hustunique.jianguo.driclient.service.DribbbleShotsService;
import com.hustunique.jianguo.driclient.service.factories.ApiServiceFactory;
import com.hustunique.jianguo.driclient.ui.adapters.CommentsAdapter;
import com.hustunique.jianguo.driclient.ui.widget.DividerItemDecoration;
import com.hustunique.jianguo.driclient.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShotCommentActivity extends BaseActivity {
    public static final String COMMENTS_SHOTS = "comments_shots";
    private Shots mShot;

    @Bind(R.id.comments_count)
    TextView mCommentsTitie;
    @Bind(R.id.comments_detail)
    TextView mCommentsSubtitle;
    @Bind(R.id.comments_progress)
    ProgressBar mProgress;
    @Bind(R.id.comments_list)
    RecyclerView mComments;
    @Bind(R.id.avatar_comments)
    CircleImageView mAvatar;
    @Bind(R.id.btn_send_comments)
    FloatingActionButton mSend;
    @Bind(R.id.et_add_comment)
    EditText mEditText;

    private CommentsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ButterKnife.bind(this);
        mShot = (Shots) getIntent().getSerializableExtra(COMMENTS_SHOTS);
        if (mShot == null || TextUtils.isEmpty(mShot.getComments_url())) {
            throw new NullPointerException("shot in ShotCommentActivity mustn't be null");
        }
        initView();

    }

    private void initView() {
        mCommentsTitie.setText(String.format(AppData.getString(R.string.comments_title)
                , mShot.getComments_count()));
        mCommentsSubtitle.setText(String.format(AppData.getString(R.string.comments_subtitle)
                , mShot.getTitle()
                , mShot.getUser().getName()));
        Picasso.with(this).load(Uri.parse(UserManager.getCurrentUser().getUser().getAvatar_url()))
                .into(mAvatar);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = mEditText.getText().toString();
                if (TextUtils.isEmpty(comment)) return;
                sendComments(comment);
            }
        });
        initComments();
    }

    //TODO: incorrect position of recyclerView
    private void sendComments(@NonNull String comment) {
        CommonUtils.hideSoftInputFromWindow(this);
        ApiServiceFactory.createService(DribbbleShotsService.class)
                .addComment(mShot.getId(), comment)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Comments>() {
                    @Override
                    public void onCompleted() {
                        mEditText.setText("");
                        Log.i("driclient", "send comment success!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            if (exception.code() == 403) {
                                showMessage("Wops! You haven't have such permission!");
                            }
                        }
                        mEditText.setText("");
                        Log.wtf("driclient", e);
                    }

                    @Override
                    public void onNext(Comments comments) {
                        mAdapter.addData(comments);
                        mComments.scrollToPosition(mAdapter.getItemCount() - 1);
                    }
                });

    }

    private void initComments() {
        mAdapter = new CommentsAdapter(this, R.layout.item_comments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mComments.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));
        mComments.setLayoutManager(linearLayoutManager);
        mComments.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        Map<String, String> params = new HashMap<>();
        params.put("per_page", "100");
        ApiServiceFactory.createService(DribbbleShotsService.class)
                .getComment(mShot.getId(), params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Comments>>() {
                    @Override
                    public void onCompleted() {
                        mProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.wtf("driclient", e);
                    }

                    @Override
                    public void onNext(List<Comments> commentses) {
                        mAdapter.setDataBefore(commentses);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
