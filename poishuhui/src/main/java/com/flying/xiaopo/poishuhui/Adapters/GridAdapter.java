package com.flying.xiaopo.poishuhui.Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flying.xiaopo.poishuhui.Beans.ItemBean;
import com.flying.xiaopo.poishuhui.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 网格布局适配器
 * Created by lenovo on 2015/8/14.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CellViewHolder> {
    Context context;

    protected LayoutInflater inflater;

    List<ItemBean> mData;

    OnItemClickListener onItemClickListener;


    public GridAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    /**
     * 提供获取数据的方法
     *
     */
    public void obtainData(List<ItemBean> data) {
        mData = data;
    }

    public List<ItemBean> getData() {
        return mData;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new CellViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CellViewHolder holder, final int position) {
        Glide.with(context).load(mData.get(position).getImageURL()).into(holder.iv_content);
        holder.tv_title.setText(mData.get(position).getTitle());
        //holder.iv_content.setTag(mData.get(position).getLink());
        holder.iv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, mData.get(position).getLink(), Snackbar.LENGTH_SHORT).show();
                if (onItemClickListener != null) onItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class CellViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_content)
        ImageView iv_content;
        @InjectView(R.id.tv_title)
        TextView tv_title;

        public CellViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public TextView getTv_title() {
            return tv_title;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
