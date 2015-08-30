package com.flying.xiaopo.opreader.View;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.flying.xiaopo.opreader.Bean.Info;
import com.flying.xiaopo.opreader.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by lenovo on 2015/5/31.
 */
public class ManHuaActivity extends Activity {
    @InjectView(R.id.manhua_container)
    LinearLayout manhuaContainer;
    @InjectView(R.id.vf_manhua)
    ViewFlipper vf_manhua;
    @InjectView(R.id.iv_manhua_1)
    ImageView iv_manhua_1;
    @InjectView(R.id.iv_manhua_2)
    ImageView iv_manhua_2;

    private List<String> urls;
    private Handler handler;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhua);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        init();
        initListener();

    }


    private void init() {
        ManHuaTask task = new ManHuaTask();
        task.execute(getIntent().getStringExtra("url"));
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                load1();
            }
        };

    }

    private void initListener() {

        vf_manhua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < urls.size()) {
                    if (index % 2 == 0) {
                        load1();
                    } else {
                        load2();
                    }
                    vf_manhua.showNext();
                }
            }
        });
    }


    private class ManHuaTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {

            return parseHtml(params[0]);
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            EventBus.getDefault().post(new Info(strings));
        }
    }

    public List<String> parseHtml(String url) {
        List<String> urls = new ArrayList<>();
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.err.println("Error");
            e.printStackTrace();
        }
        Elements element1 = document.select("p");


        for (org.jsoup.nodes.Element element : element1.select("img[src][alt]")) {
            if (element.select("img").attr("src").isEmpty()) {
                continue;
            }
            System.out.println(element.attr("src"));
            urls.add(element.attr("src"));
        }
        return urls;
    }

    //eventbus 获取数据数据
    public void onEventMainThread(Info info) {
        urls = info.getStrings();
        handler.sendEmptyMessage(0);
    }

    private void load1() {
        Glide.with(this).load(urls.get(index++)).crossFade().fitCenter().into(iv_manhua_1);

    }

    private void load2() {
        Glide.with(this).load(urls.get(index++)).crossFade().fitCenter().into(iv_manhua_2);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
