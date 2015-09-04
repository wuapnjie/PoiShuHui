package com.flying.xiaopo.poishuhui.Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flying.xiaopo.poishuhui.Beans.ChildItemBean;
import com.flying.xiaopo.poishuhui.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * news的adapter
 * Created by lenovo on 2015/9/3.
 */
public class ChildNewsAdapter extends RecyclerView.Adapter<ChildNewsAdapter.ChildViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<ChildItemBean> child_data;

    public ChildNewsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void obtainData(List<ChildItemBean> child_data) {
        this.child_data = child_data;
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.childnewsitem, parent, false);
        return new ChildViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {
        final ChildItemBean bean = child_data.get(position);
        int resId = position % 2 == 0 ? R.color.news_gray : R.color.news_white;
        holder.child_news_container.setBackgroundResource(resId);
        holder.tv_news_title.setText(bean.getChildTitle());
        holder.tv_created_time.setText(bean.getCreatedTime());
        holder.child_news_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, bean.getLink(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return child_data == null ? 0 : child_data.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_news_title)
        TextView tv_news_title;
        @InjectView(R.id.tv_created_time)
        TextView tv_created_time;
        @InjectView(R.id.child_news_container)
        LinearLayout child_news_container;

        public ChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
