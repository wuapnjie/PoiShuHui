package com.flying.xiaopo.shader.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.flying.xiaopo.shader.Adapter.TipsRecyclerAdapter;
import com.flying.xiaopo.shader.ClockDBHelper;
import com.flying.xiaopo.shader.R;
import com.flying.xiaopo.shader.Utils;
import com.flying.xiaopo.shader.View.PullToRefresh;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity implements PullToRefresh.OnHeadButtonClickListener, TipsRecyclerAdapter.OnItemClickListener, TipsRecyclerAdapter.OnItemTouchListener, TipsRecyclerAdapter.OnDelClickListener {
    @InjectView(R.id.container_main)
    RelativeLayout container_main;
    @InjectView(R.id.pulltorefrech)
    PullToRefresh pullToRefresh;
    @InjectView(R.id.rv_reminds)
    RecyclerView rv_reminds;

    RecyclerView.LayoutManager layoutManager;
    TipsRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        container_main.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                container_main.getViewTreeObserver().removeOnPreDrawListener(this);
                //animateLayout();
                return true;
            }
        });
        init();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.obtainData(new ClockDBHelper(getApplicationContext()).getAllBeans());
        adapter.setOnItemClickListener(this);
        adapter.setOnItemTouchListener(this);
        adapter.setOnDelClickListener(this);
        rv_reminds.setAdapter(adapter);
    }

    private void animateLayout() {
        pullToRefresh.setAlpha(0f);
        pullToRefresh.setTranslationX(-Utils.dpToPx(200));
        pullToRefresh.animate().alpha(1f).translationX(0).setDuration(300).setInterpolator(new AccelerateInterpolator()).start();
    }

    private void init() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new TipsRecyclerAdapter(this);
        rv_reminds.setLayoutManager(layoutManager);
        rv_reminds.setItemAnimator(new DefaultItemAnimator());
    }

    private void initListener() {
        pullToRefresh.setOnHeadButtonClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHeadButtonClick(View view) {
        toAddReminder(view);
    }

    private void toAddReminder(View view) {
        Intent intent = new Intent();
        intent.setClass(this, AddTipActivity.class);
        int[] position = new int[2];
        view.getLocationInWindow(position);
        intent.putExtra("location", position);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onItemClick(View v, MotionEvent event) {
        int ID = (int) v.getTag();
        Intent intent = new Intent();
        int[] location = new int[2];
        location[0] = (int) event.getRawX();
        location[1] = (int) event.getRawY();
        //v.getLocationInWindow(location);
        intent.putExtra("location", location);
        intent.putExtra("id", ID);
        Log.d(AddTipActivity.TAG, "ID=" + ID);

        intent.setClass(v.getContext(), AddTipActivity.class);
        v.getContext().startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onItemTouch(View v, MotionEvent event) {

    }


    @Override
    public void onDelClick(View view,int pos) {
        adapter.removeItem((int)view.getTag(),pos);
        onResume();
    }
}
