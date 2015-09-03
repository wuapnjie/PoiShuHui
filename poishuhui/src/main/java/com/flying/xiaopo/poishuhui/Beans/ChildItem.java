package com.flying.xiaopo.poishuhui.Beans;

/**
 * Created by lenovo on 2015/9/3.
 */
public class ChildItem {
    private String childTitle;
    private String cretedTime;

    public ChildItem(String childTitle, String cretedTime) {
        this.childTitle = childTitle;
        this.cretedTime = cretedTime;
    }

    public ChildItem() {

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
