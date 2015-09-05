package com.flying.xiaopo.poishuhui.Adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flying.xiaopo.poishuhui.Adapters.Impl.OnCellClickListener;
import com.flying.xiaopo.poishuhui.Beans.ItemBean;
import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.VolleyUtils.BitmapHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 网格Adapter
 * Created by xiaopo on 2015/8/14.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.CellViewHolder> {
    Context context;

    protected LayoutInflater inflater;

    List<ItemBean> mData;


    OnCellClickListener onCellClickListener;

    //MyCache myCache;

    //RequestQueue mQueue;

    public GridAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
        //mQueue = Volley.newRequestQueue(context);
        //myCache = new MyCache();
    }

    /**
     * 提供外部获取数据的方法
     */
    public void obtainData(List<ItemBean> data) {
        mData = data;
    }

    public List<ItemBean> getData() {
        return mData;
    }

    public void setOnCellClickListener(OnCellClickListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }

    @Override
    public CellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new CellViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CellViewHolder holder, final int position) {
//        Glide.with(context).load(mData.get(position).getImageURL()).into(holder.iv_content);
        String url = mData.get(position).getImageURL();
//        if (myCache.getBitmap(url) != null)
//            holder.iv_content.setImageBitmap(myCache.getBitmap(url));
//        else {
//            ImageLoader loader = new ImageLoader(mQueue, myCache);
//            ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.iv_content, R.color.default_color, R.mipmap.ic_launcher);
//            loader.get(url, listener, 200, 200);
//        }

        BitmapHelper.load2ImageView(context.getApplicationContext(), holder.iv_content, url, 200, 200,null);

        holder.tv_title.setText(mData.get(position).getTitle());
        //holder.iv_content.setTag(mData.get(position).getLink());
        holder.iv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, mData.get(position).getLink(), Snackbar.LENGTH_SHORT).show();
                if (onCellClickListener != null) onCellClickListener.onCellClick(v, position);
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
}
