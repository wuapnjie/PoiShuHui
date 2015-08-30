package com.flying.xiaopo.shader.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

/**
 * Created by lenovo on 2015/6/6.
 */
public class CircleRevealLayout extends LinearLayout {
    public CircleRevealLayout(Context context) {
        super(context);
        init();
    }

    public CircleRevealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public CircleRevealLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        Log.d("VIEW", getWidth()+"width"+getHeight()+"height");
    }



    public static final int STATE_NOT_START = 0;
    public static final int STATE_FILL_START = 1;
    public static final int STATE_END = 2;
    private int state = STATE_NOT_START;


    private Paint fillPaint;


    private int currentRadius;
    private int startLocationX;
    private int startLocationY;
    private static final Interpolator INTERPOLATOR = new AccelerateInterpolator();
    private ObjectAnimator revealAnimator;

    private void init() {
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.WHITE);
        invalidate();
    }

    public void startAnimateFromLocation(int[] location) {
        changeState(STATE_FILL_START);
        startLocationX = location[0];
        startLocationY = location[1];
        revealAnimator = ObjectAnimator.ofInt(this, "currentRadius", 0, getWidth() + getHeight()).setDuration(400);
        Log.d("VIEW",getWidth()+getHeight()+"width+height");
        revealAnimator.setInterpolator(INTERPOLATOR);
        revealAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                changeState(STATE_END);
            }
        });
        revealAnimator.start();
    }

    public void setCurrentRadius(int currentRadius) {
        this.currentRadius = currentRadius;
        Log.d("VIEW",currentRadius+"");
        invalidate();
    }

    private void changeState(int state) {
        if (this.state == state) return;
        Log.d("VIEW",state+"state");
        this.state = state;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("VIEW",getWidth()+"onDraw");
        if (state == STATE_END) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), fillPaint);
            setVisibility(GONE);
        } else {
            canvas.drawCircle(startLocationX, startLocationY, currentRadius, fillPaint);
        }
    }
}
