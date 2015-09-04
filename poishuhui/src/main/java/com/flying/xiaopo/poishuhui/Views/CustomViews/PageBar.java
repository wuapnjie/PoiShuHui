package com.flying.xiaopo.poishuhui.Views.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.flying.xiaopo.poishuhui.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 页面选择器
 * Created by lenovo on 2015/8/19.
 */
public class PageBar extends FrameLayout implements View.OnClickListener {
    View container;
    String prefixURL;


    @InjectView(R.id.btn_page_1)
    Button btn_page_1;
    @InjectView(R.id.btn_page_2)
    Button btn_page_2;
    @InjectView(R.id.btn_page_3)
    Button btn_page_3;
    @InjectView(R.id.btn_page_4)
    Button btn_page_4;
    @InjectView(R.id.btn_page_5)
    Button btn_page_5;
    @InjectView(R.id.btn_page_6)
    Button btn_page_6;

    onPageItemClickListener onPageItemClickListener;


    public PageBar(Context context) {
        super(context);
        init();
        initListener();
    }

    public PageBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initListener();
    }

    public PageBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initListener();
    }

    private void initListener() {
        btn_page_1.setOnClickListener(this);
        btn_page_2.setOnClickListener(this);
        btn_page_3.setOnClickListener(this);
        btn_page_4.setOnClickListener(this);
        btn_page_5.setOnClickListener(this);
        btn_page_6.setOnClickListener(this);
    }

    private void init() {
        container = LayoutInflater.from(getContext()).inflate(R.layout.pagebar, this, false);
        ButterKnife.inject(this,container);
        addView(container, 0);
        btn_page_1.setTag(1);
        btn_page_2.setTag(2);
        btn_page_3.setTag(3);
        btn_page_4.setTag(4);
        btn_page_5.setTag(5);
        btn_page_6.setTag(0);
    }

    public void setPrefixURL(String prefixURL) {
        this.prefixURL = prefixURL;
    }
    public String getPrefixURL() {
        return prefixURL;
    }
    public void setOnPageItemClickListener(PageBar.onPageItemClickListener onPageItemClickListener) {
        this.onPageItemClickListener = onPageItemClickListener;
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        switch (tag) {
            case 0:
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                initializeTag();
                if (onPageItemClickListener != null) onPageItemClickListener.onPageItemClick(v);
                break;
            default:
                changeTag(tag);
                if (onPageItemClickListener != null) onPageItemClickListener.onPageItemClick(v);
                break;
        }
    }

    private void changeTag(int tag) {
        btn_page_2.setTag(0);
        btn_page_2.setText("...");
        btn_page_4.setTag(tag);
        btn_page_4.setText(tag + "");
        btn_page_3.setTag(tag - 1);
        btn_page_3.setText((tag - 1) + "");
        btn_page_5.setTag(tag + 1);
        btn_page_5.setText((tag + 1) + "");
    }

    private void initializeTag() {
        btn_page_2.setTag(2);
        btn_page_2.setText("2");
        btn_page_4.setTag(4);
        btn_page_4.setText("4");
        btn_page_3.setTag(3);
        btn_page_3.setText("3");
        btn_page_5.setTag(5);
        btn_page_5.setText("5");
    }



    public interface onPageItemClickListener {
        void onPageItemClick(View v);
    }
}
