package com.flying.xiaopo.opreader.View;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flying.xiaopo.opreader.Bean.MenuBean;
import com.flying.xiaopo.opreader.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2015/5/31.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> implements View.OnClickListener {



    public interface OnCellClickListener{
        void onCellClick(View view);
    }

    public void setOnCellClickListener(OnCellClickListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }

    private OnCellClickListener onCellClickListener;
    private List<MenuBean> beans;
    private Context context;

    public DataAdapter(Context context){
        this.context = context;
    }

    public void ObtainData(List<MenuBean> beans){
        this.beans = beans;
    }




    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_menu, parent, false);
        return new DataViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        MenuBean bean = beans.get(position);
        holder.tv_title.setText(bean.getTitle());
        holder.cell_container.setTag(bean.getAutolink());
        Glide.with(context).load(bean.getUrl_pic()).centerCrop().placeholder(R.drawable.p11).crossFade().into(holder.iv_small_pic);
        holder.cell_container.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       if (onCellClickListener!=null){
           onCellClickListener.onCellClick(v);
       }
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }


    public static class DataViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.cell_container)
        CardView cell_container;
        @InjectView(R.id.iv_small_pic)
        ImageView iv_small_pic;
        @InjectView(R.id.tv_title)
        TextView tv_title;

        public DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
