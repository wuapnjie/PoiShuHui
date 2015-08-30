package com.flying.xiaopo.shader.View;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flying.xiaopo.shader.Activity.AddTipActivity;
import com.flying.xiaopo.shader.R;

/**
 * Created by lenovo on 2015/7/26.
 */
public class CellTip extends LinearLayout {
    public CellTip(Context context) {
        super(context);
    }

    public CellTip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CellTip(Context context, AttributeSet attrs) {
        super(context, attrs);
        changeState(STATE_HIDEING);
    }

    public OnTipClickListener onTipClickListener;




    private ImageButton tv_delete;

    public static final int STATE_SHOWING = 0;
    public static final int STATE_HIDEING = 1;

    private int state;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_delete = (ImageButton) findViewById(R.id.tv_delete);

    }

    public void setOnTipClickListener(OnTipClickListener onTipClickListener) {
        this.onTipClickListener = onTipClickListener;
    }


    float startX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float X = -1;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                Log.d(AddTipActivity.TAG, "startX=" + startX);
                //tv_delete.animate().translationX(-100).setDuration(200).start();
                break;
            case MotionEvent.ACTION_MOVE:
                X = event.getX();
                Log.d(AddTipActivity.TAG, "X=" + X);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(AddTipActivity.TAG, "Action_UP");
                if (state == STATE_HIDEING) {
                    if (onTipClickListener != null)
                        onTipClickListener.onTipClick(this, event);
                    return false;
                } else {
                    hideView();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                X = event.getX();
                Log.d(AddTipActivity.TAG, "startX-X" + Math.abs(X - startX));
                Log.d(AddTipActivity.TAG, "this.getWidth()/5=" + this.getWidth() / 5);
                if (startX - X >= this.getWidth() / 5) {
                    showHideView();
                } else if (state == STATE_SHOWING) {
                    hideView();
                }
                Log.d(AddTipActivity.TAG, "Action_CANCEL");
                break;
        }
        return true;
    }

    private void hideView() {
        changeState(STATE_HIDEING);
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                tv_delete, "x", tv_delete.getX(), tv_delete.getX() + tv_delete.getWidth());
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    private void showHideView() {
        changeState(STATE_SHOWING);
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                tv_delete, "x", tv_delete.getX(), tv_delete.getX() - tv_delete.getWidth());
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    public void changeState(int state) {
        this.state = state;
    }

    public interface OnTipClickListener {
        void onTipClick(View v, MotionEvent event);
    }

}
