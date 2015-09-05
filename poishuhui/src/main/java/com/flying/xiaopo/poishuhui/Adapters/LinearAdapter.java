package com.flying.xiaopo.poishuhui.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.flying.xiaopo.poishuhui.Beans.ComicBean;
import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.VolleyUtils.BitmapHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 线性布局的adapter
 * Created by lenovo on 2015/8/23.
 */
public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.TextViewHolder> implements View.OnClickListener {
    LayoutInflater inflater;

    List<ComicBean> mData;

    Context context;

    RequestQueue mQueue;

    MyCache mCache;

    public LinearAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mQueue = Volley.newRequestQueue(context);
        mCache = new MyCache();
    }

    public void obtainData(List<ComicBean> beans) {
        this.mData = beans;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.text_image, parent, false);
        return new TextViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        ComicBean comicBean = mData.get(position);
        if (!comicBean.getText().equals("") && !comicBean.getPicURL().equals("")) {
            holder.tv_text.setVisibility(View.VISIBLE);
            holder.tv_text.setText(comicBean.getText());
            holder.iv_image.setVisibility(View.VISIBLE);
            holder.iv_image.setOnClickListener(this);

            BitmapHelper.load2ImageView(context.getApplicationContext(), holder.iv_image, comicBean.getPicURL(), 200, 200, null);

        } else if (!comicBean.getText().equals("")) {
            holder.iv_image.setVisibility(View.GONE);
            holder.tv_text.setVisibility(View.VISIBLE);
            holder.tv_text.setText(comicBean.getText());
        } else if (!comicBean.getPicURL().equals("")) {
            holder.tv_text.setVisibility(View.GONE);
            holder.iv_image.setVisibility(View.VISIBLE);

            BitmapHelper.load2ImageView(context.getApplicationContext(), holder.iv_image, comicBean.getPicURL(), 200, 200, null);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    //TODO
    @Override
    public void onClick(View v) {

    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_text)
        TextView tv_text;
        @InjectView(R.id.iv_image)
        ImageView iv_image;

        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    private class MyCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public MyCache() {
            int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            mCache = new LruCache<String, Bitmap>(maxMemory / 8) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String s) {
            return mCache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            mCache.put(s, bitmap);
        }
    }
}
