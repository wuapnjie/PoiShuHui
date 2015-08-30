package com.flying.xiaopo.shader;

import android.content.res.Resources;

/**
 * Created by lenovo on 2015/6/6.
 */
public class Utils {
    public static int dpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
