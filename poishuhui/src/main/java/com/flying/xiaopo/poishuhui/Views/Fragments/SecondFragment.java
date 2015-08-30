package com.flying.xiaopo.poishuhui.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flying.xiaopo.poishuhui.Adapters.GridAdapter;
import com.flying.xiaopo.poishuhui.Adapters.StaggeredAdapter;
import com.flying.xiaopo.poishuhui.Beans.ItemBean;
import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.HtmlTask;
import com.flying.xiaopo.poishuhui.Utils.HtmlUtil;
import com.flying.xiaopo.poishuhui.Views.Activities.ComicActivity;
import com.flying.xiaopo.poishuhui.Views.CustomViews.PageBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * ∆Ÿ≤º≤ºæ÷  ≈‰∆˜
 * Created by lenovo on 2015/8/19.
 */
public class SecondFragment extends Fragment implements PageBar.onPageItemClickListener, GridAdapter.OnItemClickListener {
    public static final String INTENT_KEY_LINK = "link";

    @InjectView(R.id.rv_second_list)
    RecyclerView rv_second_list;

    @InjectView(R.id.pagebar)
    PageBar pageBar;

    StaggeredGridLayoutManager manger;

    GridAdapter adapter;

    Context context;

    List<ItemBean> list_data;

    private int currentPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        context = getActivity();
        manger = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new StaggeredAdapter(context);
        list_data = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        ButterKnife.inject(this, rootView);
        manger = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_second_list.setLayoutManager(manger);
        rv_second_list.setAdapter(adapter);
        pageBar.setPrefixURL(HtmlUtil.URL_COMIC_PREFIX);
        pageBar.setOnPageItemClickListener(this);
        startLoad(HtmlUtil.URL_COMIC);
        return rootView;
    }

    private void startLoad(String URL) {
        HtmlTask task = new HtmlTask() {
            @Override
            protected void onPostExecute(List<ItemBean> baseBeans) {
                super.onPostExecute(baseBeans);
                adapter.obtainData(baseBeans);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(SecondFragment.this);
                //rv_second_list.setAdapter(adapter);
            }
        };
        task.execute(URL, HtmlTask.TASK_ITEM);
    }

    @Override
    public void onPageItemClick(View v) {
        int clickPage = (int) v.getTag();
        if (currentPage == clickPage) return;
        startLoad(pageBar.getPrefixURL() + clickPage);
        currentPage = clickPage;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_KEY_LINK,adapter.getData().get(position).getLink());
        intent.setClass(context,ComicActivity.class);
        startActivity(intent);
    }
}
