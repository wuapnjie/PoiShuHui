package com.flying.xiaopo.opreader.View;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.flying.xiaopo.opreader.Bean.MenuBean;
import com.flying.xiaopo.opreader.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, DataAdapter.OnCellClickListener {

    @InjectView(R.id.pb_loading)
    ProgressBar pb_loading;

    @InjectView(R.id.swl_refresh)
    SwipeRefreshLayout swl_refresh;
    @InjectView(R.id.rv_menu)
    RecyclerView rv_menu;

    RecyclerView.LayoutManager manager;
    DataAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();
        initListener();
    }

    private void init() {
        manager = new LinearLayoutManager(this);
        adapter = new DataAdapter(this);
        rv_menu.setLayoutManager(manager);
        MyTask task = new MyTask();
        task.execute("http://www.ishuhui.com/archives/category/zaixianmanhua/op");
    }

    private void initListener() {
        swl_refresh.setOnRefreshListener(this);
        adapter.setOnCellClickListener(this);
    }

    @Override
    public void onRefresh() {
        swl_refresh.setRefreshing(true);
        MyTask task = new MyTask();
        task.execute("http://www.ishuhui.com/archives/category/zaixianmanhua/op");
    }

    @Override
    public void onCellClick(View view) {
        Toast.makeText(this, (String) view.getTag(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("url", (String) view.getTag());
        intent.setClass(this, ManHuaActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public class MyTask extends AsyncTask<String, Void, List<MenuBean>> {

        @Override
        protected List<MenuBean> doInBackground(String... params) {

            return parseHtml(params[0]);
        }

        @Override
        protected void onPostExecute(List<MenuBean> menuBeans) {
            super.onPostExecute(menuBeans);
            if (menuBeans == null) {
                pb_loading.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_SHORT).show();
            } else {
                pb_loading.setVisibility(View.GONE);
                adapter.ObtainData(menuBeans);
                rv_menu.setAdapter(adapter);
                swl_refresh.setRefreshing(false);
            }
        }
    }

    public List<MenuBean> parseHtml(String url) {
        List<MenuBean> menuBeans = new ArrayList<>();
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
            Elements element1 = document.select("section div.row");


            StringBuffer stringBuffer = new StringBuffer();
            for (org.jsoup.nodes.Element element : element1.select("a[href][title]")) {
                if (element.select("img").attr("src").isEmpty()) {
                    continue;
                }
                MenuBean bean = new MenuBean();
                bean.setAutolink(element.attr("href"));
                bean.setTitle(element.attr("title"));
                bean.setUrl_pic(element.select("img").attr("src"));
                menuBeans.add(bean);

                stringBuffer.append(element.attr("href"));
                stringBuffer.append("\n");
                stringBuffer.append(element.attr("title"));
                stringBuffer.append("\n");
                stringBuffer.append(element.select("img").attr("src"));
                stringBuffer.append("\n");
                stringBuffer.append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error");
            e.printStackTrace();
        }

        return menuBeans;
    }


}
