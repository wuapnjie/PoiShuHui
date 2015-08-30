package com.flying.xiaopo.poishuhui.Utils;

import android.os.AsyncTask;

import com.flying.xiaopo.poishuhui.Beans.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2015/8/14.
 */
public class HtmlTask extends AsyncTask<String, Void, List<ItemBean>> {
    public static final String TASK_SLIDE = "slide";
    public static final String TASK_ITEM = "Item";


    @Override
    protected List<ItemBean> doInBackground(String... params) {
        List<ItemBean> list = new ArrayList<>();
        //list = HtmlUtil.obtainSlide(params[0]);
        switch (params[1]) {
            case TASK_SLIDE:
                list = HtmlUtil.obtainSlide(params[0]);
                break;
            case TASK_ITEM:
                list = HtmlUtil.obtainComicList(params[0]);
                break;
        }
        return list;
    }

}
