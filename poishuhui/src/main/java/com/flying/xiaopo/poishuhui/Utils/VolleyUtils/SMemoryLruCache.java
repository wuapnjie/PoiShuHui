package com.flying.xiaopo.poishuhui.Utils.VolleyUtils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.flying.xiaopo.poishuhui.BuildConfig;

/**
 * 单例的内存缓存类
 * Created by lenovo on 2015/9/5.
 */
public class SMemoryLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    private final String TAG = this.getClass().getSimpleName();
    private static SMemoryLruCache mInstance;

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    private SMemoryLruCache(int maxSize) {
        super(maxSize);
    }

    public static SMemoryLruCache getInstance() {
        if (mInstance == null) {
            int maxMem = (int) Runtime.getRuntime().maxMemory();
            mInstance = new SMemoryLruCache(maxMem / 8);
        }
        return mInstance;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String s) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "Retrieved item from Mem Cache");
        }
        return this.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, "Added item to Mem Cache");
        }
        this.put(s, bitmap);
    }
}
