package com.flying.xiaopo.poishuhui.Utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Glide配置项
 * Created by lenovo on 2015/8/15.
 */
public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {
        glideBuilder.setDiskCache(new InternalCacheDiskCacheFactory(context, 10 * 1024 * 1024));
        glideBuilder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
        glideBuilder.setBitmapPool(new LruBitmapPool(10 * 1024 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
