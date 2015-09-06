package com.flying.xiaopo.poishuhui.Views.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.flying.xiaopo.poishuhui.Adapters.PageAdapter;
import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Views.Fragments.ComicBookListFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2015/9/6.
 */
public class ComicDetailActivity extends AppCompatActivity {
    @InjectView(R.id.iv_detail)
    ImageView iv_detail;
    @InjectView(R.id.tv_detail_title)
    TextView tv_detail_title;
    @InjectView(R.id.tv_detail_time)
    TextView tv_detail_time;
    @InjectView(R.id.tv_detail_intro)
    TextView tv_detail_intro;
    @InjectView(R.id.rv_detail)
    RecyclerView rv_detail;

    GridLayoutManager manager;
    PageAdapter adapter;
    String taskURL;
    @InjectView(R.id.toolbar_detail)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        ButterKnife.inject(this);
        init();
        taskURL = getIntent().getStringExtra(ComicBookListFragment.INTENT_KEY);
        System.out.println(taskURL);
    }

    private void init() {
        manager = new GridLayoutManager(this, 4);
        adapter = new PageAdapter(this);
        rv_detail.setLayoutManager(manager);
        rv_detail.setAdapter(adapter);

        mToolbar.setLogo(R.drawable.ic_logo);
    }
}
