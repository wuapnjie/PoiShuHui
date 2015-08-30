package com.flying.xiaopo.poishuhui.Views.Activities;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.flying.xiaopo.poishuhui.Beans.ComicBean;
import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.HtmlUtil;
import com.flying.xiaopo.poishuhui.Views.Fragments.SecondFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by lenovo on 2015/8/20.
 */
public class ComicActivity extends AppCompatActivity {
    @InjectView(R.id.vp_manhua)
    ViewPager vp_manhua;

    LayoutInflater inflater;

    String aimURL;

    List<View> pagers;

    ComicAdapter adapter;

    RequestQueue mQueue;

    MyCache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        ButterKnife.inject(this);
        init();
        aimURL = getIntent().getStringExtra(SecondFragment.INTENT_KEY_LINK);

        //System.out.println(aimURL);

        obtainPicURLs(aimURL);
    }

    private void init() {
        inflater = LayoutInflater.from(this);
        pagers = new ArrayList<>();
        adapter = new ComicAdapter();
        mQueue = Volley.newRequestQueue(this);
        cache = new MyCache();
        vp_manhua.setAdapter(adapter);
    }

    private void obtainPicURLs(String aimURL) {
        Task task = new Task();
        task.execute(aimURL);
    }

    public class ComicAdapter extends PagerAdapter {
        List<ComicBean> datas;

        public void obtainData(List<ComicBean> beans) {
            datas = beans;
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View rootView = inflater.inflate(R.layout.comic_view, container, false);
            final PhotoView pv_comic = (PhotoView) rootView.findViewById(R.id.pv_comic);
            final LinearLayout fail_view = (LinearLayout) rootView.findViewById(R.id.fail_view);
            ImageLoader loader = new ImageLoader(mQueue, cache);
//            ImageLoader.ImageListener listener = ImageLoader.getImageListener(pv_comic, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean b) {
                    fail_view.setVisibility(View.GONE);
                    if(response.getBitmap() != null) {
                        pv_comic.setImageBitmap(response.getBitmap());
                    } else {
                        pv_comic.setImageResource(R.color.default_color);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    pv_comic.setVisibility(View.INVISIBLE);
                    fail_view.setVisibility(View.VISIBLE);
                }
            };
            loader.get(datas.get(position).getPicURL(), listener, MainActivity.DEVICE_WIDTH, MainActivity.DEVICE_HEIGHT);
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
            //super.destroyItem(container, position, object);
        }
    }

    /**
     * 内部网络任务类，获取图片URLs
     */
    private class Task extends AsyncTask<String, Void, List<ComicBean>> {

        @Override
        protected List<ComicBean> doInBackground(String... params) {
            return HtmlUtil.obtainComicContent(params[0]);
        }

        @Override
        protected void onPostExecute(List<ComicBean> comicBeans) {
            super.onPostExecute(comicBeans);
            adapter.obtainData(comicBeans);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 内部缓存类
     */
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

    /**
     * 内存紧张时调用
     */
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_UI_HIDDEN:
                System.gc();
                break;
        }
    }
}
