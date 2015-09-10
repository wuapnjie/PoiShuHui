package com.flying.xiaopo.poishuhui.Utils;

import com.flying.xiaopo.poishuhui.Beans.ChildItemBean;
import com.flying.xiaopo.poishuhui.Beans.ComicBean;
import com.flying.xiaopo.poishuhui.Beans.ContainerBean;
import com.flying.xiaopo.poishuhui.Beans.ItemBean;
import com.flying.xiaopo.poishuhui.Beans.PageBean;

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
    public static final String URL_NEWS = "http://ishuhui.net/CMS/";
    public static final String URL_NEWS_PREFIX = "http://ishuhui.net/";

    public static final String TEXT_TIME = "time";
    public static final String TEXT_DETAIL = "detail";


    public static Document obtainDocument(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 获取Slide中的内容
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
     */
    public static List<ItemBean> obtainComicBookList(String url) {
        Document document = obtainDocument(url);
        List<ItemBean> list = new ArrayList<>();
        if (document == null) return null;

        Elements elements = document.select("ul.chinaMangaContentList").select("li");

        for (Element element : elements) {

            ItemBean bean = new ItemBean();
            if (element.select("img").attr("src").contains("http"))
                bean.setImageURL(element.select("img").attr("src"));
            else bean.setImageURL("http://ishuhui.net" + element.select("img").attr("src"));
            bean.setTitle(element.select("p").text());
            bean.setLink("http://ishuhui.net" + element.select("div.chinaMangaContentImg").select("a").attr("href"));

            list.add(bean);
        }
        return list;
    }

    /**
     * 获取漫画内容
     *
     * @param url
     * @return
     */
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

    /**
     * 获取动漫资讯中的内容
     *
     * @param url
     * @return
     */
    public static List<ContainerBean> obtainContainer(String url) {
        Document document = obtainDocument(url);
        List<ContainerBean> list = new ArrayList<>();
        if (document == null) return null;

        Elements elements = document.select("div.reportersBox").select("div.reportersMain");

        for (Element element : elements) {

            ContainerBean containerBean = new ContainerBean();
            containerBean.setTitle(element.select("div.mangeListTitle").select("span").text());

            List<ChildItemBean> childItemList = new ArrayList<>();

            for (Element ele : element.select("ul.reportersList").select("li").select("a")) {
                ChildItemBean childItemBean = new ChildItemBean();
                childItemBean.setLink(URL_NEWS_PREFIX + ele.attr("href"));
                childItemBean.setChildTitle(ele.select("span").get(0).text().replace("&amp", "\t"));
                childItemBean.setCreatedTime(ele.select("span").get(1).text());
                childItemList.add(childItemBean);
            }
            containerBean.setChildDataList(childItemList);
            list.add(containerBean);
        }
        return list;
    }

    /**
     * 获取动漫详情中的页码内容
     *
     * @param url
     * @return
     */
    public static List<PageBean> obtainPageList(String url) {
        Document document = obtainDocument(url);

        if (document == null) return null;
        List<PageBean> list = new ArrayList<>();
        Elements elements = document.select("div.volumeControl").select("a");

        for (Element element : elements) {
            PageBean bean = new PageBean();
            bean.setText(element.text());
            bean.setLink("http://ishuhui.net/" + element.attr("href"));
            list.add(bean);
        }
        return list;
    }

    /**
     * 获取最新更新时间
     *
     * @param url
     * @param what
     * @return
     */
    public static String obtainText(String url, String what) {
        Document document = obtainDocument(url);

        String text;
        if (document == null) return null;
        switch (what) {
            case TEXT_TIME:
                text = document.select("div.mangaInfoDate").text();
                break;
            case TEXT_DETAIL:
                text = document.select("div.mangaInfoTextare").text();
                break;
            default:
                text = "";
                break;
        }
        return text;
    }

}
