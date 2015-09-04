package com.flying.xiaopo.poishuhui.Utils;

/**
 * 缓存类
 * Created by lenovo on 2015/9/3.
 */

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;


public class MyCache implements ImageLoader.ImageCache {

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