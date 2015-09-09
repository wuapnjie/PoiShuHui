package com.flying.xiaopo.poishuhui.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flying.xiaopo.poishuhui.Adapters.Impl.OnCellClickListener;
import com.flying.xiaopo.poishuhui.Beans.PageBean;
import com.flying.xiaopo.poishuhui.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * PageAdapter
 * Created by lenovo on 2015/9/6.
 */
public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder> {
    private LayoutInflater inflater;



    private List<PageBean> datas;

    OnCellClickListener onCellClickListener;

    public PageAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public List<PageBean> getDatas() {
        return datas;
    }

    public void setOnCellClickListener(OnCellClickListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }


    public void obtainData(List<PageBean> datas) {
        this.datas = datas;
    }

    @Override
    public PageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.pageitem, parent, false);
        return new PageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PageViewHolder holder, final int position) {
        holder.tv_page.setText(datas.get(position).getText());
        holder.tv_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCellClickListener != null) onCellClickListener.onCellClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    public class PageViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_page)
        TextView tv_page;

        public PageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
