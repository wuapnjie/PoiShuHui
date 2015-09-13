package com.flying.xiaopo.poishuhui.Views.CustomViews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by xiaopo on 2015/9/12.
 */
public class SunnyLoad extends View {
    //View's width
    private int VIEW_WIDTH;
    //View's height
    private int VIEW_HEIGHT;
    //画笔
    private Paint mPaint;

    //圆弧的半径
    private float mRadius;

    //圆弧的起始角度
    private float mStartAngel = 0f;

    //缓存位图
    private Bitmap mCacheBitmap;

    //缓存画布
    private Canvas mCacheCanvas;

    //是否为第一个动画
    private boolean isFirstAnim = true;

    //最大半径
    private float maxRadius = -1;

    //最小半径
    private float minRadius = -1;

    //光线长度
    private float mLineLength;

    //光线最大长度
    private float maxLineLength = -1;

    //一次完整动画是否结束的标志
    private boolean isFinished = false;

    private RectF mRectF;

    private int index = 0;

    private static final int INDEX_ONE = 0;
    private static final int INDEX_TWO = 1;
    private static final int INDEX_THREE = 2;
    private static final int INDEX_FOUR = 3;
    private static final int INDEX_FIVE = 4;
    private static final int[] INDEXES = new int[]{INDEX_ONE, INDEX_TWO, INDEX_THREE, INDEX_FOUR, INDEX_FIVE};

    public SunnyLoad(Context context) {
        super(context);
        init();
    }

    public SunnyLoad(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SunnyLoad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRectF = new RectF();
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(30f);

        post(new Runnable() {
            @Override
            public void run() {
                startFirstAnim(0);
            }
        });

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        VIEW_WIDTH = getWidth();
        VIEW_HEIGHT = getHeight();
        mCacheBitmap = Bitmap.createBitmap(VIEW_WIDTH, VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        mCacheCanvas = new Canvas(mCacheBitmap);
    }

    /**
     * 设置画笔的颜色和粗细
     *
     * @param color 画笔颜色
     * @param width 画笔粗细
     */
    public void setColorAndPaintWidth(int color, float width) {
        mPaint.setColor(color);
        mPaint.setStrokeWidth(width);

        invalidate();
    }

    private void startFirstAnim(long delayed) {
        if (maxRadius == -1) maxRadius = this.VIEW_WIDTH / 6.7f;
        if (minRadius == -1) minRadius = this.VIEW_WIDTH / 30;
        if (maxLineLength == -1) maxLineLength = this.getMaxRadius() / 3;

//        System.out.println("FirstAnim");

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator angel_animator = ObjectAnimator.ofFloat(this, "startAngel", -60, 450);
        ObjectAnimator radius_animator = ObjectAnimator.ofFloat(this, "radius", minRadius, maxRadius);
        animatorSet.playTogether(angel_animator, radius_animator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setIsFirstAnim(false);
                changeIndex(INDEX_ONE);
                startSecondAnim();
                super.onAnimationEnd(animation);
            }
        });
        animatorSet.setDuration(2500);
        animatorSet.setStartDelay(delayed);
        animatorSet.start();

    }

    private void startSecondAnim() {
//        System.out.println("SecondAnim");
        ObjectAnimator length_animator = ObjectAnimator.ofFloat(this, "lineLength", 0, maxLineLength);
        length_animator.setDuration(200);
        length_animator.setInterpolator(new AccelerateInterpolator());
        length_animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (index != INDEXES.length) {
                    changeIndex(index + 1);
                    startSecondAnim();
                } else {
                    setIsFinished(true);
                }
            }
        });
        length_animator.start();
    }

    private void changeIndex(int index) {
        this.index = index;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isFinished) {
            //清除缓存
            mCacheCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            startFirstAnim(200);
            setIsFinished(false);
            return;
        }
        mRectF.left = VIEW_WIDTH / 2.06f - mRadius;
        mRectF.right = VIEW_WIDTH / 2.06f + mRadius;
        mRectF.top = VIEW_HEIGHT / 2 - mRadius;
        mRectF.bottom = VIEW_HEIGHT / 2 + mRadius;

        mCacheCanvas.drawArc(mRectF, mStartAngel, 14, false, mPaint);
        canvas.drawBitmap(mCacheBitmap, 0, 0, mPaint);
        if (!isFirstAnim) {
            switch (index) {
                case INDEX_ONE:
                    mCacheCanvas.drawLine(VIEW_WIDTH / 2 - (maxRadius + VIEW_WIDTH / 23.5f) * 0.9397f, VIEW_HEIGHT / 2 - (maxRadius + VIEW_WIDTH / 23.5f) * 0.3420f, VIEW_WIDTH / 2 - ((maxRadius + VIEW_WIDTH / 23.5f) + mLineLength) * 0.9397f, VIEW_HEIGHT / 2 - ((maxRadius + VIEW_WIDTH / 23.5f) + mLineLength) * 0.3420f, mPaint);
                    canvas.drawBitmap(mCacheBitmap, 0, 0, mPaint);
                    break;
                case INDEX_TWO:
                    mCacheCanvas.drawLine(VIEW_WIDTH / 2 - (maxRadius + VIEW_WIDTH / 23.5f) * 0.5000f, VIEW_HEIGHT / 2 - (maxRadius + VIEW_WIDTH / 23.5f) * 0.8660f, VIEW_WIDTH / 2 - ((maxRadius + VIEW_WIDTH / 23.5f) + mLineLength) * 0.5000f, VIEW_HEIGHT / 2 - ((maxRadius + VIEW_WIDTH / 23.5f) + mLineLength) * 0.8660f, mPaint);
                    canvas.drawBitmap(mCacheBitmap, 0, 0, mPaint);
                    break;
                case INDEX_THREE:
                    mCacheCanvas.drawLine(VIEW_WIDTH / 2 + (maxRadius + VIEW_WIDTH / 23.5f) * 0.1736f, VIEW_HEIGHT / 2 - (maxRadius + VIEW_WIDTH / 23.5f) * 0.9848f, VIEW_WIDTH / 2 + ((maxRadius + VIEW_WIDTH / 23.5f) + mLineLength) * 0.1736f, VIEW_HEIGHT / 2 - ((maxRadius + VIEW_WIDTH / 23.5f) + mLineLength) * 0.9848f, mPaint);
                    canvas.drawBitmap(mCacheBitmap, 0, 0, mPaint);
                    break;
                case INDEX_FOUR:
                    mCacheCanvas.drawLine(VIEW_WIDTH / 2 + (maxRadius + VIEW_WIDTH / 23.5f) * 0.7660f, VIEW_HEIGHT / 2 - (maxRadius + VIEW_WIDTH / 23.5f) * 0.6428f, VIEW_WIDTH / 2 + ((maxRadius + VIEW_WIDTH / 23.5f) + mLineLength) * 0.7660f, VIEW_HEIGHT / 2 - ((maxRadius + VIEW_WIDTH / 23.5f) + mLineLength) * 0.6428f, mPaint);
                    canvas.drawBitmap(mCacheBitmap, 0, 0, mPaint);
                    break;
                case INDEX_FIVE:
                    mCacheCanvas.drawLine(VIEW_WIDTH / 2 + (maxRadius + VIEW_WIDTH / 23.5f), VIEW_HEIGHT / 2, VIEW_WIDTH / 2 + ((maxRadius + VIEW_WIDTH / 23.5f) + mLineLength), VIEW_HEIGHT / 2, mPaint);
                    canvas.drawBitmap(mCacheBitmap, 0, 0, mPaint);
                    break;
            }

        }

    }

    public void setMaxRaius(float maxRadius) {
        this.maxRadius = maxRadius;
    }

    public float getMaxRaius() {

        return maxRadius;
    }

    public float getStartAngel() {
        return mStartAngel;
    }

    public void setStartAngel(float startAngel) {
        mStartAngel = startAngel;
        invalidate();
    }


    public float getLineLength() {
        return mLineLength;
    }

    public float getMaxLineLength() {
        return maxLineLength;
    }

    public void setMaxLineLength(float maxLineLength) {
        this.maxLineLength = maxLineLength;
    }

    public void setLineLength(float lineLength) {
        mLineLength = lineLength;
        invalidate();
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
        invalidate();
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    public Bitmap getCacheBitmap() {
        return mCacheBitmap;
    }

    public void setCacheBitmap(Bitmap cacheBitmap) {
        mCacheBitmap = cacheBitmap;
    }

    public Canvas getCacheCanvas() {
        return mCacheCanvas;
    }

    public void setCacheCanvas(Canvas cacheCanvas) {
        mCacheCanvas = cacheCanvas;
    }

    public boolean isFirstAnim() {
        return isFirstAnim;
    }

    private void setIsFirstAnim(boolean isFirstAnim) {
        this.isFirstAnim = isFirstAnim;
    }

    public float getMinRadius() {
        return minRadius;
    }

    public void setMinRadius(float minRadius) {
        this.minRadius = minRadius;
    }

    public float getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(float maxRadius) {
        this.maxRadius = maxRadius;
    }

    public boolean isFinished() {
        return isFinished;
    }

    private void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }
}