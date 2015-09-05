package com.flying.xiaopo.poishuhui.Utils.VolleyUtils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * 单例的RequestQueue类
 * Created by lenovo on 2015/9/5.
 */
public class SVolleyHelper {
    private static Context mCtx;
    private ImageLoader mLoadr;
    private SMemoryLruCache memoryLruCache;
    private RequestQueue mQueue;
    private static SVolleyHelper mInstance;

    private SVolleyHelper(Context context) {
        mCtx = context;

        mQueue=getRequestQueue();

        memoryLruCache = SMemoryLruCache.getInstance();

        mLoadr = new ImageLoader(mQueue,memoryLruCache);
    }

    public static synchronized SVolleyHelper getInstance(Context context){
        if (mInstance==null){
            mInstance=new SVolleyHelper(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mQueue==null){
            mQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mQueue;
    }

    public ImageLoader getLoader(){
        return mLoadr;
    }

}
