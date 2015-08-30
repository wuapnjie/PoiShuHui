package com.flying.xiaopo.butterknifetest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity {
    @InjectView(R.id.rv_items)
    RecyclerView rv_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_items.setLayoutManager(linearLayoutManager);
        rv_items.setAdapter(new MyAdapter());
    }

}
