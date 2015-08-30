package com.flying.xiaopo.volleytest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.btn_sendrequest)
    Button btn_sendrequest;
    @InjectView(R.id.tv_html)
    TextView tv_html;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mQueue = Volley.newRequestQueue(this);

    }


    @OnClick(R.id.btn_sendrequest)
    public void sendRequest() {
        MyTask task = new MyTask();
        task.execute("http://www.ishuhui.com/archives/374476");
        System.out.println("click");
    }


    public String parseHtml(String url) {
        Document document = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.err.println("Error");
            e.printStackTrace();
        }
        List<ComicBean> beanList = new ArrayList<>();

        Elements elements = document.select("div.article-content");
        stringBuffer.append(elements.select("p,h4").toString()+"\n\n\n");

        for (Element element : elements.select("p,h4")) {
            stringBuffer.append(element.toString()+"\n\n\n");

            ComicBean bean = new ComicBean();

            bean.setText(element.text());

            //System.out.println(element.text());

            Elements elements_src = element.select("img[src]");

            if (elements_src.size() == 0) {
                bean.setPicURL("");
                beanList.add(bean);
            } else {
                for (int i = 0; i < elements_src.size(); i++) {
                    if (i == 0) {
                        bean.setPicURL(elements_src.get(i).attr("src"));
                        beanList.add(bean);
                    } else {
                        ComicBean bean1 = new ComicBean();
                        bean1.setText("");
                        bean1.setPicURL(elements_src.get(i).attr("src"));
                        beanList.add(bean1);
                    }
                }
            }
        }
        for (ComicBean bean1 : beanList) {
            System.out.println(bean1.getText() + bean1.getPicURL());
        }
//        Elements element1 = document.select("p");
//
//
//        ????????????
////        for (int i = 0;i<element1.select("a[href][title]").size();i++){
////            stringBuffer.append(element1.get(i).attr("href"));
////            stringBuffer.append("\n");
////            stringBuffer.append(element1.get(i).attr("title"));
////            stringBuffer.append("\n");
////            stringBuffer.append(element1.get(i).select("img").attr("src"));
////            stringBuffer.append("\n");
////            stringBuffer.append("\n");
////        }
//        for (org.jsoup.nodes.Element element : element1.select("img[src][alt]")) {
//            if (element.select("img").attr("src").isEmpty()){
//                continue;
//            }
////            stringBuffer.append(element.attr("href"));
////            stringBuffer.append("\n");
////            stringBuffer.append(element.attr("title"));
////            stringBuffer.append("\n");
//            stringBuffer.append(element.select("img").attr("src"));
//            stringBuffer.append("\n");
//            stringBuffer.append("\n");
//        }

        return stringBuffer.toString();
    }

    public class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            return parseHtml(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv_html.setText(s);
        }
    }

    public class ComicBean {
        private String text;
        private String picURL;

        public String getPicURL() {
            return picURL;
        }

        public void setPicURL(String picURL) {
            this.picURL = picURL;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
