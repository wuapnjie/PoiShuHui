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
import com.flying.xiaopo.poishuhui.Views.Activities.TextActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2015/8/20.
 */
public class ThirdFragment extends Fragment implements GridAdapter.OnItemClickListener {
    public static final String INTENT_KEY = "torv";

    @InjectView(R.id.rv_third_list)
    RecyclerView rv_third_list;

    StaggeredGridLayoutManager manager;

    StaggeredAdapter adapter;

    String taskURL = null;

    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        adapter = new StaggeredAdapter(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_third, container, false);
        ButterKnife.inject(this, rootView);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_third_list.setLayoutManager(manager);
        rv_third_list.setAdapter(adapter);
        if (taskURL != null) startLoad(taskURL);
        return rootView;
    }

    public String getTaskURL() {
        return taskURL;
    }

    public void setTaskURL(String taskURL) {
        this.taskURL = taskURL;
    }

    protected void startLoad(String URL) {
        HtmlTask task = new HtmlTask() {
            @Override
            protected void onPostExecute(List<ItemBean> baseBeans) {
                super.onPostExecute(baseBeans);
                adapter.obtainData(baseBeans);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(ThirdFragment.this);
            }
        };
        task.execute(URL, HtmlTask.TASK_ITEM);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_KEY,adapter.getData().get(position).getLink());
        intent.setClass(context,TextActivity.class);
        startActivity(intent);
    }
}
