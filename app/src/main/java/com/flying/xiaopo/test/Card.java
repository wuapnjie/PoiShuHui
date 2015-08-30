package com.flying.xiaopo.test;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lenovo on 2015/5/6.
 */
public class Card extends CardView {
    public Card(Context context) {
        super(context);
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float x = -1;
    float y = -1;
    int radius = 0;


    public void setRippleSpeed(int rippleSpeed) {
        this.rippleSpeed = rippleSpeed;
    }

    int rippleSpeed = 8;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                x = event.getX();
//                y = event.getY();
                radius = getHeight() / rippleSpeed;
//                drawRipple();
                System.out.println("down");
                break;
            case MotionEvent.ACTION_MOVE:
                x = -1;
                y = -1;
                System.out.println("move");
                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                drawRipple();

                System.out.println("up");
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (x != -1) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#88DDDDDD"));
            canvas.drawCircle(x, y, radius, paint);
            invalidate();
        }
    }

    private void drawRipple() {
        ValueAnimator animator = ValueAnimator.ofInt(getHeight() / rippleSpeed, getHeight() / 2 * 3);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                if (value < getHeight() / rippleSpeed + getHeight() / 6) {
                    radius = (int) animation.getAnimatedValue();
                } else {
                    radius = getHeight() / 2 * 3;
                }
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                x = -1;
                y = -1;
                radius = 0;
            }
        });

        animator.start();
    }
}
