package com.flying.xiaopo.poishuhui.Utils.VolleyUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.flying.xiaopo.poishuhui.BuildConfig;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lenovo on 2015/9/5.
 */
public class SDiskLruCache {
    private static Context mCtx;
    private static SDiskLruCache mInstance;
    private DiskLruCache mDiskLruCache;
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;
    private static int IO_BUFFER_SIZE = 8 * 1024;
    private int mCompressQuality = 70;
    private static final int BUFFER_SIZE = 10 * 1024 * 1024;
    private static final int VALUE_COUNT = 1;

    private SDiskLruCache(Context context) {
        mCtx = context;
        try {
            File cacheDir = getDiskCacheDir("mycache");
            if (!cacheDir.exists()) cacheDir.mkdirs();
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(), VALUE_COUNT, BUFFER_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized static SDiskLruCache getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SDiskLruCache(context);
        }
        return mInstance;
    }

    public void putBitmap(String url, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        String cacheUrl = hashKeyForDisk(url);
        try {
            editor = mDiskLruCache.edit(cacheUrl);
            if (editor == null) return;
            if (writeBitmapToFile(bitmap, editor)) {
                mDiskLruCache.flush();
                editor.commit();
                if (BuildConfig.DEBUG) {
                    Log.d("cache_test_DISK_", "image put on disk cache " + url);
                }
            } else {
                editor.abort();
                if (BuildConfig.DEBUG) {
                    Log.d("cache_test_DISK_", "ERROR on: image put on disk cache " + url);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (BuildConfig.DEBUG) {
                Log.d("cache_test_DISK_", "ERROR on: image put on disk cache " + url);
            }
            try {
                if (editor != null) {
                    editor.abort();
                }
            } catch (IOException ignored) {
            }
        }
    }

    private boolean writeBitmapToFile(Bitmap bitmap, DiskLruCache.Editor editor) throws IOException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(editor.newOutputStream(0), IO_BUFFER_SIZE);
            return bitmap.compress(mCompressFormat, mCompressQuality, out);
        } finally {
            if (out != null) out.close();
        }

    }

    public Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        String cacheUrl = hashKeyForDisk(url);
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskLruCache.get(cacheUrl);
            if (snapshot == null) return null;
            final InputStream in = snapshot.getInputStream(0);
            if (in != null) {
                final BufferedInputStream bufferedInputStream = new BufferedInputStream(in, IO_BUFFER_SIZE);
                bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) snapshot.close();
        }
        return bitmap;
    }


    private int getAppVersion() {
        try {
            PackageInfo info = mCtx.getPackageManager().getPackageInfo(mCtx.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private File getDiskCacheDir(String uniqName) {
        String cacheDir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cacheDir = mCtx.getExternalCacheDir().getPath();
        } else cacheDir = mCtx.getCacheDir().getPath();
        return new File(cacheDir + File.separator + uniqName);
    }

    public void seCompressQuality(int mCompressQuality) {
        this.mCompressQuality = mCompressQuality;
    }

    public String hashKeyForDisk(String url) {
        String cacheUrl;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            cacheUrl = bytes2HexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheUrl = String.valueOf(url.hashCode());
        }
        return cacheUrl;
    }

    private String bytes2HexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
