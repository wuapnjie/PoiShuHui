package com.flying.xiaopo.poishuhui.Views.Activities;
/**
 * 主界面，由多个fragment组成
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.flying.xiaopo.poishuhui.R;
import com.flying.xiaopo.poishuhui.Utils.HtmlUtil;
import com.flying.xiaopo.poishuhui.Utils.Utils;
import com.flying.xiaopo.poishuhui.Views.CustomViews.SunnyLoad;
import com.flying.xiaopo.poishuhui.Views.Fragments.ComicBookListFragment;
import com.flying.xiaopo.poishuhui.Views.Fragments.ComicListFragment;
import com.flying.xiaopo.poishuhui.Views.Fragments.ComicNewsFragment;
import com.flying.xiaopo.poishuhui.Views.Fragments.MainFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {
    @InjectView(R.id.ll_container)
    CoordinatorLayout ll_container;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.mViewpager)
    ViewPager mViewPager;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.et_search)
    EditText et_search;
    @InjectView(R.id.search_container)
    LinearLayout search_container;
    @InjectView(R.id.ibtn_exit_search)
    ImageButton ibtn_exit_search;
    @InjectView(R.id.searchbar)
    CardView searchbar;
    @InjectView(R.id.sunnyload)
    SunnyLoad mSunnyLoad;

    List<Fragment> pagers;

    MainFragment mainFragment;
    ComicListFragment comicListFragment;
    ComicBookListFragment comicBookListFragment;
    ComicNewsFragment comicNewsFragment;

    Handler mHandler;

    public static final int DEVICE_WIDTH;
    public static final int DEVICE_HEIGHT;

    static {
        DEVICE_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
        DEVICE_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
        System.out.println(DEVICE_WIDTH);
        System.out.println(DEVICE_HEIGHT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();
    }

    /**
     * 一些
     * 初始化的设置
     */
    private void init() {
        toolbar.setLogo(R.drawable.ic_logo);
//        toolbar.setTitle(R.string.app_name);
//        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.menu_start);
        toolbar.setOnMenuItemClickListener(this);

        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        fab.setOnClickListener(this);

        pagers = new ArrayList<>();
        mainFragment = new MainFragment();
        comicListFragment = new ComicListFragment();
        comicBookListFragment = new ComicBookListFragment();
        comicNewsFragment = new ComicNewsFragment();

        comicBookListFragment.setTaskURL(HtmlUtil.URL_COMIC_LIST);

        pagers.add(mainFragment);
        pagers.add(comicListFragment);
        pagers.add(comicBookListFragment);
        pagers.add(comicNewsFragment);

        et_search.setHint(R.string.search_hint);
        et_search.setTextColor(Color.GRAY);
        ibtn_exit_search.setOnClickListener(this);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mSunnyLoad.setVisibility(View.GONE);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                showSearchBar(this);
                break;
            case R.id.ibtn_exit_search:
                hideSearchbar(this);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                showSearchBar(this);
                break;
        }
        return false;
    }

    /**
     * ViewPager's adapter
     */
    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        public static final int PAGER_NUM = 4;
        int[] titles = new int[]{R.string.tab_1, R.string.tab_2, R.string.tab_3, R.string.tab_4};

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pagers.get(position);
        }

        @Override
        public int getCount() {
            return PAGER_NUM;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(titles[position]);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

    /**
     * 使搜索栏显示
     *
     * @param ctx
     */
    private void showSearchBar(final Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Animator show_animator = ViewAnimationUtils.createCircularReveal(searchbar, searchbar.getWidth() - Utils.dp2px(56), Utils.dp2px(23), 0, (float) Math.hypot(searchbar.getWidth(), searchbar.getHeight()));
            show_animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    ll_container.setClickable(true);
                    search_container.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    et_search.requestFocus();
                    ((InputMethodManager) ctx.getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(et_search, 0);
                }
            });
            show_animator.setDuration(300);
            show_animator.start();
        } else {
            search_container.setVisibility(View.VISIBLE);
            et_search.requestFocus();
            ((InputMethodManager) ctx.getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(et_search, 0);
        }
    }

    /**
     * 隐藏搜索栏
     *
     * @param ctx
     */
    private void hideSearchbar(final Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Animator hide_animator = ViewAnimationUtils.createCircularReveal(searchbar, searchbar.getWidth() - Utils.dp2px(56), Utils.dp2px(23), (float) Math.hypot(searchbar.getWidth(), searchbar.getHeight()), 0);
            hide_animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    search_container.setVisibility(View.INVISIBLE);
                    ll_container.setClickable(false);
                    ((InputMethodManager) ctx.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(searchbar.getWindowToken(), 0);
                }
            });
            hide_animator.setDuration(300);
            hide_animator.start();
        } else {
            search_container.setVisibility(View.INVISIBLE);
            ((InputMethodManager) ctx.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(searchbar.getWindowToken(), 0);
        }
    }

    public Handler getHandler() {
        return mHandler;
    }
}
