package com.flying.xiaopo.poishuhui.Beans;

/**
 * 漫画内容的bean
 * Created by lenovo on 2015/8/20.
 */
public class ComicBean{
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
