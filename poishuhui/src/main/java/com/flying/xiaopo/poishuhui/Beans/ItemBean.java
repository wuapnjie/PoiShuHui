package com.flying.xiaopo.poishuhui.Beans;

/**
 * 一些图片标题的Item的Bean
 * Created by lenovo on 2015/8/14.
 */
public class ItemBean {
    //ͼƬ��URL
    private String imageURL;
    //����
    private String title;
    //ͼƬ����
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
