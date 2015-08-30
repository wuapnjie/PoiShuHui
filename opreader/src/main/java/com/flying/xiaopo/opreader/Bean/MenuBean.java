package com.flying.xiaopo.opreader.Bean;

/**
 * ´æ´¢ÐÅÏ¢µÄjavabean
 * Created by lenovo on 2015/5/31.
 */
public class MenuBean {
    public String autolink;
    public String Url_pic;
    public String title;

    public MenuBean() {
    }

    public MenuBean(String title, String url_pic) {
        this.title = title;
        Url_pic = url_pic;
    }
    public void setAutolink(String autolink) {
        this.autolink = autolink;
    }

    public String getAutolink() {

        return autolink;
    }




    public void setUrl_pic(String url_pic) {
        Url_pic = url_pic;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getUrl_pic() {

        return Url_pic;
    }

    public String getTitle() {

        return title;
    }
}
