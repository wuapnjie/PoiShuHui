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
 * 解析工具类
 * Created by lenovo on 2015/8/12.
 */

public class HtmlUtil {
    public static final String URL_MAIN = "http://ishuhui.net/Index";
    public static final String URL_COMIC = "http://ishuhui.net/Index";
    public static final String URL_COMIC_PREFIX = "http://ishuhui.net/?PageIndex=";
    public static final String URL_COMIC_LIST = "http://ishuhui.net/ComicBookList/";


    public static final String URL_SHARINKAN = "http://www.ishuhui.com/archives/category/news/sharinkan";

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
     */
    public static List<ItemBean> obtainSlide(String url) {
        Document document = obtainDocument(url);
        List<ItemBean> list = new ArrayList<>();
        if (document == null) return null;

        Elements elements = document.select("ul.mangeListBox").select("li");

        for (Element element : elements) {

            ItemBean bean = new ItemBean();
            bean.setImageURL(element.select("img").attr("src"));
            bean.setTitle(element.select("h1").text() + "\n" + element.select("h2").text());
            bean.setLink("http://ishuhui.net" + element.select("div.magesPhoto").select("a").attr("href"));

            list.add(bean);
        }
        return list;
    }

    /**
     * 获取在线漫画中的信息
     *
     */
    public static List<ItemBean> obtainComicList(String url) {
        Document document = obtainDocument(url);
        List<ItemBean> list = new ArrayList<>();
        if (document == null) return null;

        Elements elements = document.select("ul.mangeListBox").select("li");

        for (Element element : elements) {

            ItemBean bean = new ItemBean();
            bean.setImageURL(element.select("img").attr("src"));
            bean.setTitle(element.select("h1").text() + "\n" + element.select("h2").text());
            bean.setLink("http://ishuhui.net" + element.select("div.magesPhoto").select("a").attr("href"));

            list.add(bean);
        }
        return list;
    }

    /**
     * 获取漫画列表中的信息
     *
     */
    public static List<ItemBean> obtainComicBookList(String url) {
        Document document = obtainDocument(url);
        List<ItemBean> list = new ArrayList<>();
        if (document == null) return null;

        Elements elements = document.select("ul.chinaMangaContentList").select("li");

        for (Element element : elements) {

            ItemBean bean = new ItemBean();
            bean.setImageURL(element.select("img").attr("src"));
            bean.setTitle(element.select("p").text());
            bean.setLink("http://ishuhui.net" + element.select("div.chinaMangaContentImg").select("a").attr("href"));

            list.add(bean);
        }
        return list;
    }


    public static List<ComicBean> obtainComicContent(String url) {
        Document document = obtainDocument(url);
        List<ComicBean> list = new ArrayList<>();
        if (document == null) return null;

        Elements elements = document.select("div.mangaContentMainImg").select("img");
        for (Element element : elements) {
            ComicBean bean = new ComicBean();
            if (element.attr("src").endsWith(".png") || element.attr("src").endsWith(".jpg") || element.attr("src").endsWith(".JPEG"))
                bean.setPicURL(element.attr("src"));
            else
                bean.setPicURL(element.attr("data-original"));
            bean.setText("");

            list.add(bean);
        }
        return list;
    }
}
