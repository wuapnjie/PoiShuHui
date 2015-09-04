package com.flying.xiaopo.poishuhui.Utils;

import android.content.res.Resources;

/**
 * 工具类
 * Created by lenovo on 2015/8/19.
 */
public class Utils {
    public static int dp2px(int dp){
        return (int) (dp* Resources.getSystem().getDisplayMetrics().density);
    }
}
