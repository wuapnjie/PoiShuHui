package com.flying.xiaopo.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private ImageView imageView;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private List<Bitmap> list_bitmap;

    private LruCache<String, Bitmap> lruCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        imageView = (ImageView) findViewById(R.id.imageView);
//
//
//        imageView.setImageBitmap(BitmapUtils.decodeBitmap(getResources(), R.drawable.p1, 100, 100));


        init();

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        System.out.println("最大内存为" + maxMemory + "KB");
        int cacheSize = maxMemory / 8;
        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                System.out.println(bitmap.getByteCount() / 1024);
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            lruCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return lruCache.get(key);
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this,3);
        adapter = new RecyclerAdapter(getResources(), new int[]{R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10});

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Integer... params) {
            final Bitmap bitmap = BitmapUtils.decodeBitmap(getResources(), params[0], 100, 100);
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }
    }

}
