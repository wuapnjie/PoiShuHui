package com.flying.xiaopo.poishuhui.Beans;

/**
 * 菜单，Slide的Item的JavaBean
 * Created by lenovo on 2015/8/14.
 */
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
