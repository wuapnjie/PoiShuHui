package com.flying.xiaopo.poishuhui.Utils.VolleyUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.flying.xiaopo.poishuhui.R;

/**
 * 加载图片的工具类
 * Created by xiaopo on 2015/9/5.
 */
public class BitmapHelper {
    /**
     * 图片加载
     * @param context                最好是应用的context
     * @param imageView              填充图片的imageview
     * @param url                    网络图片的来源
     * @param width                  允许图片的最大宽度
     * @param height                 允许图片的最大高度
     * @param onLoadFinishedListener 加载成功和失败的监听
     */
    public static void load2ImageView(Context context, ImageView imageView, String url, int width, int height, OnLoadFinishedListener onLoadFinishedListener) {
        Bitmap bitmap = SMemoryLruCache.getInstance().getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            if (onLoadFinishedListener != null)
                onLoadFinishedListener.onLoadSuccessed();
        } else {
            loadFromInternet(context, imageView, url, width, height, onLoadFinishedListener);
        }
    }

    /**
     * 参数同上，磁盘和网络加载
     * @param context
     * @param imageView
     * @param url
     * @param width
     * @param height
     * @param onLoadFinishedListener
     */
    private static void loadFromInternet(Context context, final ImageView imageView, final String url, int width, int height, final OnLoadFinishedListener onLoadFinishedListener) {
        final SDiskLruCache mDiskLruCache = SDiskLruCache.getInstance(context);
        Bitmap bitmap = mDiskLruCache.getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            if (onLoadFinishedListener != null)
                onLoadFinishedListener.onLoadSuccessed();
        } else {
            //RequestQueue queue = SVolleyHelper.getInstance(context).getRequestQueue();
            ImageLoader loader = SVolleyHelper.getInstance(context).getLoader();
            ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    if (imageContainer.getBitmap() != null) {
                        imageView.setImageBitmap(imageContainer.getBitmap());
                        mDiskLruCache.putBitmap(url, imageContainer.getBitmap());
                        if (onLoadFinishedListener != null)
                            onLoadFinishedListener.onLoadSuccessed();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                    if (onLoadFinishedListener != null)
                        onLoadFinishedListener.onLoadSuccessed();
                }
            };
            loader.get(url, listener, width, height);
        }
    }

    /**
     * 加载成功和失败的监听接口
     */
    public interface OnLoadFinishedListener {
        void onLoadSuccessed();

        void onLoadFailed();
    }
}
