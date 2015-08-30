package com.flying.xiaopo.poishuhui.Views.CustomViews;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flying.xiaopo.poishuhui.Beans.ItemBean;
import com.flying.xiaopo.poishuhui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * �Զ���Slide
 * Created by lenovo on 2015/8/14.
 */
public class SlideLayout extends LinearLayout implements View.OnClickListener {
    private int bigposition = 2;

    FrameLayout fl_container[];

    ImageView iv_content[];

    TextView tv_title[];

    int[] iv_resId = new int[]{R.id.iv_content1, R.id.iv_content2, R.id.iv_content3, R.id.iv_content4, R.id.iv_content5};

    int[] tv_resId = new int[]{R.id.tv_title1, R.id.tv_title2, R.id.tv_title3, R.id.tv_title4, R.id.tv_title5};

    int[] fl_resId = new int[]{R.id.fl_container1, R.id.fl_container2, R.id.fl_container3, R.id.fl_container4, R.id.fl_container5};

    LinearLayout.LayoutParams params1, params2;

    List<ItemBean> mData;

    OnChildClickListenner onChildClickListenner;

    Handler handler;

    public SlideLayout(Context context) {
        super(context);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        init();
    }

    private void init() {
        iv_content = new ImageView[5];
        tv_title = new TextView[5];
        fl_container = new FrameLayout[5];
        params1 = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 15.5f);
        params2 = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 38.0f);
        mData = new ArrayList<>();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                anim();
                handler.sendEmptyMessageDelayed(0,2500);
            }
        };
        handler.sendEmptyMessageDelayed(0,2500);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < 5; i++) {
            iv_content[i] = (ImageView) findViewById(iv_resId[i]);
            tv_title[i] = (TextView) findViewById(tv_resId[i]);
            fl_container[i] = (FrameLayout) findViewById(fl_resId[i]);
            fl_container[i].setTag(i);
            fl_container[i].setOnClickListener(this);
            if (i == 2) {
                fl_container[i].setLayoutParams(params2);
                tv_title[i].setVisibility(VISIBLE);
            } else {
                fl_container[i].setLayoutParams(params1);
                tv_title[i].setVisibility(INVISIBLE);
            }
        }

    }

    /**
     * �������Դ������ͼƬ
     *
     * @param data
     */
    public void obtainData(List<ItemBean> data) {
        if (data == null) {
            Snackbar.make(this, "ˢ����һ��", Snackbar.LENGTH_SHORT).show();
            return;
        }
        mData = data;
        if (data.size()==0) return;
        for (int i = 0; i < 5; i++) {
            ItemBean bean = data.get(i);
            Glide.with(getContext()).load(bean.getImageURL()).override(300, 300).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_content[i]);
            tv_title[i].setText(bean.getTitle());
        }
    }

    public List<ItemBean> getData() {
        return mData;
    }

    private void changeBigPosition(int currentPosition){
        bigposition = currentPosition;
    }

    public void setOnChildClickListenner(OnChildClickListenner onChildClickListenner) {
        this.onChildClickListenner = onChildClickListenner;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        for (int i = 0; i < 5; i++) {
            if (i == position) {
                fl_container[i].setLayoutParams(params2);
                tv_title[i].setVisibility(VISIBLE);
                if (onChildClickListenner != null&&bigposition==position) onChildClickListenner.onChildClick(v);
                changeBigPosition(position);
            } else {
                tv_title[i].setVisibility(INVISIBLE);
                fl_container[i].setLayoutParams(params1);
            }
        }


    }

    private void anim(){
        int curPos = ++bigposition%5;
        for (int i = 0; i < 5; i++) {
            if (i == curPos) {
                fl_container[i].setLayoutParams(params2);
                tv_title[i].setVisibility(VISIBLE);
                changeBigPosition(curPos);
            } else {
                tv_title[i].setVisibility(INVISIBLE);
                fl_container[i].setLayoutParams(params1);
            }
        }
    }

    /**
     * �ṩ�������ӿ�
     */
    public interface OnChildClickListenner {
        void onChildClick(View view);
    }
}