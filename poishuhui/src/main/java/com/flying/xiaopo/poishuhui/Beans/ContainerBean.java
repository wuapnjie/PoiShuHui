package com.flying.xiaopo.poishuhui.Beans;

import java.util.List;

/**
 * Created by lenovo on 2015/9/3.
 */
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
