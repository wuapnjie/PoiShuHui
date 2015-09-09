package com.flying.xiaopo.poishuhui.Views.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flying.xiaopo.poishuhui.Adapters.Impl.OnCellClickListener;
import com.flying.xiaopo.poishuhui.Adapters.PageAdapter;
import com.flying.xiaopo.poishuhui.Beans.PageBean;
import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.HtmlUtil;
import com.flying.xiaopo.poishuhui.Utils.VolleyUtils.BitmapHelper;
import com.flying.xiaopo.poishuhui.Views.Fragments.ComicBookListFragment;
import com.flying.xiaopo.poishuhui.Views.Fragments.ComicListFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2015/9/6.
 */
public class ComicDetailActivity extends AppCompatActivity implements OnCellClickListener {
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
    @InjectView(R.id.toolbar_detail)
    Toolbar mToolbar;

    private GridLayoutManager manager;
    private PageAdapter adapter;
    private String taskURL;
    private String title;
    private String imgURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            taskURL = bundle.getString(ComicBookListFragment.INTENT_KEY_LIMK);
            title = bundle.getString(ComicBookListFragment.INTENT_KEY_TITLE);
            imgURL = bundle.getString(ComicBookListFragment.INTENT_KEY_URL);
        }

        init();
        startLoad(taskURL);
    }

    private void startLoad(String taskURL) {
        PageTask task = new PageTask(){
            @Override
            protected void onPostExecute(List<PageBean> pageBeans) {
                super.onPostExecute(pageBeans);
                adapter.obtainData(pageBeans);
                adapter.notifyDataSetChanged();
                adapter.setOnCellClickListener(ComicDetailActivity.this);
            }
        };
        task.execute(taskURL);
    }

    private void init() {
        manager = new GridLayoutManager(this, 4);
        adapter = new PageAdapter(this);

        rv_detail.setLayoutManager(manager);
        rv_detail.setAdapter(adapter);

        mToolbar.setTitle(title);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_detail_title.setText(title);
        BitmapHelper.load2ImageView(getApplicationContext(), iv_detail, imgURL, 200, 200, null);
    }

    @Override
    public void onCellClick(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra(ComicListFragment.INTENT_KEY_LINK, adapter.getDatas().get(position).getLink());
        intent.setClass(this, ComicActivity.class);
        startActivity(intent);
    }

    private class PageTask extends AsyncTask<String, Void, List<PageBean>> {
        @Override
        protected List<PageBean> doInBackground(String... params) {
            return HtmlUtil.obtainPageList(params[0]);
        }
    }
}
