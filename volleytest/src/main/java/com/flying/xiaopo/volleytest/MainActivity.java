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
        task.execute("http://ishuhui.net/CMS/");
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
        List<ContainerBean> list = new ArrayList<>();

        Elements elements = document.select("div.reportersBox").select("div.reportersMain");
        stringBuffer.append(elements.toString() + "\n\n\n");

        for (Element element : elements) {

            ContainerBean containerBean = new ContainerBean();
            containerBean.setTitle(element.select("div.mangeListTitle").select("span").text());

            List<ChildItemBean> childItemList = new ArrayList<>();

            for (Element ele : element.select("ul.reportersList").select("li").select("a")) {
                ChildItemBean childItemBean = new ChildItemBean();
                childItemBean.setLink("http://ishuhui.net/" + ele.attr("href"));
                childItemBean.setChildTitle(ele.select("span").get(0).text().replace("&amp", "\t"));
                childItemBean.setCretedTime(ele.select("span").get(1).text());
                childItemList.add(childItemBean);
            }
            containerBean.setChildDataList(childItemList);
            list.add(containerBean);
        }

        for (ContainerBean bean1 : list) {
            System.out.println(bean1.getTitle());
            List<ChildItemBean> beans = bean1.getChildDataList();
            for (ChildItemBean bean : beans) {
                System.out.println(bean.getChildTitle());
                System.out.println(bean.getCretedTime());
                System.out.println(bean.getLink());
            }
            System.out.println("------------------------------------------------------------------");
        }

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

    public class ItemBean {
        //图片的URL
        private String imageURL;
        //标题
        private String title;
        //图片链接
        private String link;

        public ItemBean() {
        }

        public ItemBean(String imageURL, String title, String link) {
            this.imageURL = imageURL;
            this.title = title;
            this.link = link;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }


        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class ChildItemBean {
        private String childTitle;
        private String cretedTime;
        private String link;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }


        public String getChildTitle() {
            return childTitle;
        }

        public void setChildTitle(String childTitle) {
            this.childTitle = childTitle;
        }

        public String getCretedTime() {
            return cretedTime;
        }

        public void setCretedTime(String cretedTime) {
            this.cretedTime = cretedTime;
        }
    }

    public class ContainerBean {
        private String title;      //container's title;
        private List<ChildItemBean> childDataList;

        public ContainerBean() {
        }

        public ContainerBean(List<ChildItemBean> childDataList, String title) {
            this.childDataList = childDataList;
            this.title = title;
        }

        public List<ChildItemBean> getChildDataList() {
            return childDataList;
        }

        public void setChildDataList(List<ChildItemBean> childDataList) {
            this.childDataList = childDataList;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
