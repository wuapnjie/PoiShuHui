package com.flying.xiaopo.poishuhui.Beans;

/**
 * news的bean
 * Created by xiaopo on 2015/9/3.
 */
public class ChildItemBean {
    private String childTitle;
    private String createdTime;
    private String link;

    public ChildItemBean(String childTitle, String createdTime, String link) {
        this.childTitle = childTitle;
        this.createdTime = createdTime;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ChildItemBean() {

    }

    public String getChildTitle() {
        return childTitle;
    }

    public void setChildTitle(String childTitle) {
        this.childTitle = childTitle;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
