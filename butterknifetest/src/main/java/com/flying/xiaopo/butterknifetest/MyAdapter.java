package com.flying.xiaopo.butterknifetest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2015/8/6.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder> {
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int i) {
        viewHolder.tv_item.setText("Item\t"+i);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.tv_item)
        TextView tv_item;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }
}
