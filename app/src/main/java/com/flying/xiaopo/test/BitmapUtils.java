package com.flying.xiaopo.test;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Created by lenovo on 2015/5/11.
 */
public class BitmapUtils {
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int smallSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / reqHeight);
            final int widthRatio = Math.round((float) width / reqWidth);
            smallSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return smallSize;
    }


    public static Bitmap decodeBitmap(Resources resources,int resId,int reqwidth,int reqheight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources,resId,options);
        options.inSampleSize = calculateInSampleSize(options,reqwidth,reqheight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources,resId,options);

    }
}
