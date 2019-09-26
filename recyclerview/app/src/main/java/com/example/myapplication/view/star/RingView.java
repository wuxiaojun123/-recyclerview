package com.example.myapplication.view.star;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import com.example.myapplication.R;


public class RingView extends View {

    private final static String	TAG			= "RingView";

    private int					mDuration	= 5000;

    private float				mCx;

    private float				mCy;

    private float				mMaxRadius;														// 最大半径

    private float				mOuterCurrentProgress;

    private float				mInnerCurrentProgress;

    private int[]				mColors;

    private PorterDuffXfermode mXfermode	= new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    private Paint mPaint;															// 绘制外圆

    private Paint				mInnerPaint;

    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mColors = getContext().getResources().getIntArray(R.array.clor_ring);
        mPaint = new Paint();
        mPaint.setColor(mColors[0]);

        mInnerPaint = new Paint();
        mInnerPaint.setColor(Color.TRANSPARENT);
        mInnerPaint.setAlpha(0);
        mInnerPaint.setXfermode(mXfermode);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mCx = getMeasuredWidth() / 2;
        mCy = getMeasuredHeight() / 2;

        mMaxRadius = mCx;
    }

    @Override protected void onDraw(Canvas canvas) {
        int saveLayer = canvas.saveLayer(0, 0, mCx * 2, mCy * 2, mPaint, Canvas.ALL_SAVE_FLAG);

        canvas.drawCircle(mCx, mCx, mOuterCurrentProgress * mMaxRadius, mPaint);

        canvas.drawCircle(mCx, mCy, mInnerCurrentProgress * mMaxRadius, mInnerPaint);

        canvas.restoreToCount(saveLayer);
    }

    public void startAnim(int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mOuterCurrentProgress = (float) animation.getAnimatedValue();
                mPaint.setARGB(255, 255, (int) (229 - mOuterCurrentProgress * 68), 0);
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator()); // 先变快，然后减速

        ValueAnimator innerAnimator = ValueAnimator.ofFloat(0.01f, 1f);
        innerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mInnerCurrentProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        innerAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.playTogether(valueAnimator, innerAnimator);
        animatorSet.start();
    }

    private OnWillEndAnimatorListener onWillEndAnimatorListener;

    public void setOnWillEndAnimatorListener(OnWillEndAnimatorListener onWillEndAnimatorListener) {
        this.onWillEndAnimatorListener = onWillEndAnimatorListener;
    }

    public interface OnWillEndAnimatorListener {

        void onWillEndAnimatorListener(float value);
    }

}