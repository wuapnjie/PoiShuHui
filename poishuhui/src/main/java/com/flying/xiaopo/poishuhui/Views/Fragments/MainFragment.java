package com.flying.xiaopo.poishuhui.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flying.xiaopo.poishuhui.Adapters.GridAdapter;
import com.flying.xiaopo.poishuhui.Adapters.Impl.OnCellClickListener;
import com.flying.xiaopo.poishuhui.Beans.ItemBean;
import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.HtmlTask;
import com.flying.xiaopo.poishuhui.Utils.HtmlUtil;
import com.flying.xiaopo.poishuhui.Views.Activities.ComicActivity;
import com.flying.xiaopo.poishuhui.Views.CustomViews.SlideLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 首页Fragment
 * Created by lenovo on 2015/8/14.
 */
public class MainFragment extends Fragment implements SlideLayout.OnChildClickListenner, SwipeRefreshLayout.OnRefreshListener, OnCellClickListener {
    @InjectView(R.id.mSlideLayout)
    SlideLayout mSlideLayout;
    @InjectView(R.id.rv_bottom)
    RecyclerView rv_bottom;
    @InjectView(R.id.refresh_main)
    SwipeRefreshLayout refresh_main;

    GridLayoutManager layoutManager;
    GridAdapter adapter;

    Context context;

    List<ItemBean> data_slide;
    List<ItemBean> data_list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        context = getActivity();
        data_slide = new ArrayList<>();
        data_list = new ArrayList<>();
        layoutManager = new GridLayoutManager(context, 2);
        adapter = new GridAdapter(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        layoutManager = new GridLayoutManager(context, 2);
        rv_bottom.setLayoutManager(layoutManager);
        rv_bottom.setAdapter(adapter);
        refresh_main.setOnRefreshListener(this);
        refresh_main.setRefreshing(true);
        onRefresh();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void startLoad() {
        HtmlTask task1 = new HtmlTask() {
            @Override
            protected void onPostExecute(List<ItemBean> baseBeans) {
                super.onPostExecute(baseBeans);
                data_slide = baseBeans;
                mSlideLayout.obtainData(baseBeans);
                mSlideLayout.setOnChildClickListenner(MainFragment.this);

            }
        };
        task1.execute(HtmlUtil.URL_MAIN, HtmlTask.TASK_SLIDE);

        HtmlTask task2 = new HtmlTask() {
            @Override
            protected void onPostExecute(List<ItemBean> baseBeans) {
                super.onPostExecute(baseBeans);
                data_list = baseBeans;
                adapter.obtainData(baseBeans);
                adapter.notifyDataSetChanged();
                adapter.setOnCellClickListener(MainFragment.this);
            }
        };
        task2.execute(HtmlUtil.URL_MAIN, HtmlTask.TASK_ITEM);

    }

    @Override
    public void onChildClick(View view) {
        if (data_slide != null) {
//            String link = data_slide.get((int) view.getTag()).getLink();
//            Snackbar.make(view, link, Snackbar.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra(ComicListFragment.INTENT_KEY_LINK, data_slide.get((int) view.getTag()).getLink());
            intent.setClass(context, ComicActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        refresh_main.setRefreshing(true);
        startLoad();

    }

    @Override
    public void onCellClick(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra(ComicListFragment.INTENT_KEY_LINK, adapter.getData().get(position).getLink());
        intent.setClass(context, ComicActivity.class);
        startActivity(intent);
    }
}
