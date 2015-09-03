package com.flying.xiaopo.poishuhui.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flying.xiaopo.poishuhui.Beans.ContainerBean;
import com.flying.xiaopo.poishuhui.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 第四个Fragment的Adapter
 * Created by lenovo on 2015/9/3.
 */
public class ContainerLinearAdapter extends RecyclerView.Adapter<ContainerLinearAdapter.NewsContainerViewHolder> {
    private List<ContainerBean> container_data;

    private Context context;

    private LayoutInflater inflater;

    public ContainerLinearAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void obtainData(List<ContainerBean> data) {
        container_data = data;
    }

    @Override
    public NewsContainerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.news_container_item, parent, false);
        return new NewsContainerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsContainerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
//        return container_data == null ? 0 : container_data.size();
    }

    public class NewsContainerViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_container_title)
        TextView tv_container_title;
        @InjectView(R.id.tv_more)
        TextView tv_more;

        public NewsContainerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
