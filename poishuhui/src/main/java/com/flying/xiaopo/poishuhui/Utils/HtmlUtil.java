package com.flying.xiaopo.poishuhui.Utils;

import com.flying.xiaopo.poishuhui.Beans.ComicBean;
import com.flying.xiaopo.poishuhui.Beans.ItemBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2015/8/12.
 */

public class HtmlUtil {
    public static final String URL_MAIN = "http://www.ishuhui.com/";
    public static final String URL_COMIC = "http://www.ishuhui.com/archives/category/zaixianmanhua/";
    public static final String URL_COMIC_PREFIX = "http://www.ishuhui.com/archives/category/zaixianmanhua/page/";
    public static final String URL_SBS = "http://www.ishuhui.com/archives/category/sbs";
    public static final String URL_SHARINKAN = "http://www.ishuhui.com/archives/category/news/sharinkan";
    public static final String URL_NEWS = "http://www.ishuhui.com/archives/category/news/news8";
    public static final String URL_COLORS = "http://www.ishuhui.com/archives/category/news/colors";
    public static Document obtainDocument(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.err.println("Jsoup Error");
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 获取Slide中的内容
     *
     * @param url
     * @return
     */
    public static List<ItemBean> obtainSlide(String url) {
        Document document = obtainDocument(url);
        List<ItemBean> list = new ArrayList<>();
        if (document == null) return null;

        Elements elements = document.select("div#slide").select("div[style]");
        for (Element element : elements) {
            ItemBean itemBean = new ItemBean();
            String string = element.attr("style");
            itemBean.setImageURL(string.substring(string.indexOf("(") + 1, string.indexOf(")")));
            itemBean.setTitle(element.select("a").attr("title").replace("<br>", "\n"));
            itemBean.setLink(element.select("a").attr("href"));
            list.add(itemBean);
        }
        return list;
    }

    /**
     * 获取漫画列表中的信息
     *
     * @param url
     * @return
     */
    public static List<ItemBean> obtainComicList(String url) {
        Document document = obtainDocument(url);
        List<ItemBean> list = new ArrayList<>();
        if (document == null) return null;

        Elements elements = document.select("div.row").select("div.thumbnail");
        for (Element element : elements) {
            ItemBean bean = new ItemBean();
            bean.setImageURL(element.select("img").attr("src"));
            bean.setTitle(element.select("a").attr("title").replace("<br>", "\n"));
            bean.setLink(element.select("a").attr("href"));
            list.add(bean);
        }
        return list;
    }

    public static List<ComicBean> obtainComicContent(String url) {
        Document document = obtainDocument(url);
        List<ComicBean> list = new ArrayList<>();
        if (document == null) return null;

        Elements elements = document.select("div.article-content").select("p,h4");
        for (Element element : elements) {

            ComicBean bean = new ComicBean();

            bean.setText("\t\t"+element.text()+"\n");

            Elements elements_src = element.select("img[src]");

            if (elements_src.size() == 0) {
                bean.setPicURL("");
                list.add(bean);
            } else {
                for (int i = 0; i < elements_src.size(); i++) {
                    if (i == 0) {
                        bean.setPicURL(elements_src.get(i).attr("src"));
                        list.add(bean);
                    } else {
                        ComicBean bean1 = new ComicBean();
                        bean1.setText("");
                        bean1.setPicURL(elements_src.get(i).attr("src"));
                        list.add(bean1);
                    }
                }
            }
        }
        return list;
    }
}
