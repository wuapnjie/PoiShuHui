package com.flying.xiaopo.shader.View;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.flying.xiaopo.shader.R;
import com.flying.xiaopo.shader.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lenovo on 2015/6/6.
 */
public class PullToRefresh extends LinearLayout implements View.OnTouchListener {

    @InjectView(R.id.ibtn_add)
    ImageButton ibtn_add;

    @OnClick(R.id.ibtn_add)
    void addReminder() {
        if (onHeadButtonClickListener != null) {
            onHeadButtonClickListener.onHeadButtonClick(ibtn_add);
        }
    }

    OnHeadButtonClickListener onHeadButtonClickListener;

    View headerView;

    RecyclerView listView;

    MarginLayoutParams headerLayoutParams;

    int hideHeaderHeight;

    public PullToRefresh(Context context) {
        super(context);
    }

    public PullToRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public PullToRefresh(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);
    }

    public PullToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        headerView = LayoutInflater.from(context).inflate(R.layout.headerview, null, true);
        ButterKnife.inject(this, headerView);
        setOrientation(VERTICAL);
        addView(headerView, 0);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(ibtn_add, "translationX", Utils.dpToPx(70), 0);
                animator.setDuration(1000);
                animator.setInterpolator(new BounceInterpolator());
                animator.start();
            }
        }, 300);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            hideHeaderHeight = headerView.getHeight() / 6;
            headerLayoutParams = (MarginLayoutParams) headerView.getLayoutParams();
            headerLayoutParams.topMargin = -hideHeaderHeight;
            Log.d("PULL", "onLayout" + hideHeaderHeight);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        listView = (RecyclerView) getChildAt(1);
        listView.setOnTouchListener(this);
    }

    float down_y;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down_y = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float move_y = event.getRawY();
                int distance = (int) (move_y - down_y);
                headerView.setLayoutParams(headerLayoutParams);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    public interface OnHeadButtonClickListener {
        void onHeadButtonClick(View view);
    }


    public void setOnHeadButtonClickListener(OnHeadButtonClickListener onHeadButtonClickListener) {
        this.onHeadButtonClickListener = onHeadButtonClickListener;
    }


}
