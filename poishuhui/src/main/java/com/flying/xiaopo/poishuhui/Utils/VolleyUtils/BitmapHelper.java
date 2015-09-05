package com.flying.xiaopo.poishuhui.Utils.VolleyUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.flying.xiaopo.poishuhui.R;

/**
 * Created by lenovo on 2015/9/5.
 */
public class BitmapHelper {
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
                        onLoadFinishedListener.onLoadSuccessed();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                    onLoadFinishedListener.onLoadFailed();
                }
            };
            loader.get(url, listener, width, height);
        }
    }

    public interface OnLoadFinishedListener {
        void onLoadSuccessed();

        void onLoadFailed();
    }
}
