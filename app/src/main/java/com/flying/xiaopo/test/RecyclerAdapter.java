package com.flying.xiaopo.test;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lenovo on 2015/5/8.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private int[] mData;
    private List<Bitmap> mList;
    private Resources resources;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(ImageView v) {
            super(v);
            imageView = v;
        }
    }

    public RecyclerAdapter(Resources resources, int[] data) {
        this.mData = data;
        this.resources = resources;
    }

    public RecyclerAdapter(List<Bitmap> list_data) {
        this.mList = list_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);

        ViewHolder vh = new ViewHolder((ImageView) v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.imageView.setImageBitmap(BitmapUtils.decodeBitmap(resources, mData[position], 100, 100));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.length;
    }
}
