package com.example.myapplication.view.star;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.bean.StarModel;

import java.util.ArrayList;
import java.util.Random;

public class StarView extends View {

    private Paint mPaint;

    private Bitmap mStar;							// 星星图片

    private ArrayList<StarModel> mStarModelList	= null;

    private float					mCurrentProgress;				// 当前进度

    private int						mCenterX, mCenterY;				// 中心点

    private ValueAnimator mValueAnimator;

    public StarView(Context context) {
        this(context, null);
    }

    public StarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mStar = BitmapFactory.decodeResource(getResources(), R.mipmap.stars1);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mCenterX = getMeasuredWidth() / 2 - mStar.getWidth() / 2; // 90
        mCenterY = getMeasuredHeight() / 2 - mStar.getHeight() / 2; // 90
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mStarModelList == null) {
            return;
        }
        for (StarModel starModel : mStarModelList) {
            starModel.draw(canvas, mPaint, mCurrentProgress, mCenterX, mCenterY);
        }
    }

    public ValueAnimator startAnimation(int length, int duration, int delay) {
        if (mStarModelList != null) {
            mStarModelList.clear();
            mValueAnimator = null;
        }
        initStarModel(length);

        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.setDuration(duration);

        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.setStartDelay(delay);
        mValueAnimator.start();
        return mValueAnimator;
    }

    private void initStarModel(int length) {
        mStarModelList = new ArrayList<>(10);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            float angle = 0f;
            float startX = 0f;
            float startY = 0f;
            float speed = 1 + random.nextInt(5); // 总共是19

            float scale = 1f; // 开始大小

            if (i == 0) {
                angle = (float) ((10 + 270) * Math.PI / 180);
                startX = (float) Math.cos(angle) * mCenterX * 0.05f;
                startY = (float) Math.sin(angle) * mCenterY * 0.5f;
                // speed = 0.5789f;

            } else if (i == 1) {
                angle = (float) ((30 + 270) * Math.PI / 180);
                startX = (float) Math.cos(angle) * mCenterX * 0.1f;
                startY = (float) Math.sin(angle) * mCenterY * 0.2f;
                // speed = 0.7368f;

            } else if (i == 2) {
                angle = (float) ((40 + 270) * Math.PI / 180);
                startX = (float) Math.cos(angle) * mCenterX * 0.25f;
                startY = (float) Math.sin(angle) * mCenterY * 0.4f;
                // speed = 0.5789f;

            } else if (i == 3) {
                angle = (float) ((60 + 270) * Math.PI / 180);
                startX = (float) Math.cos(angle) * mCenterX * 0.3f;
                startY = (float) Math.sin(angle) * mCenterY * 0.33f;
                // speed = 0.5263f;

            } else if (i == 4) { // 星星变小
                angle = (float) ((50) * Math.PI / 180);
                startX = (float) Math.cos(angle) * mCenterX * 0.6f;
                startY = (float) Math.sin(angle) * mCenterY * 0.7f;
                scale = 0.8f;
                // speed = 0.3157f; // 8

            } else if (i == 5) { // 星星变更小
                angle = (float) ((80) * Math.PI / 180);
                startX = (float) Math.cos(angle) * mCenterX * 0.05f;
                startY = (float) Math.sin(angle) * mCenterY * 0.3f;
                scale = 0.6f;
                // speed = 0.4210f;

            } else if (i == 6) {
                angle = (float) ((145) * Math.PI / 180);
                startX = (float) Math.cos(angle) * mCenterX * 0.5f;
                startY = (float) Math.sin(angle) * mCenterY * 0.28f;
                // speed = 0.5263f;

            } else if (i == 7) {
                angle = (float) ((205) * Math.PI / 180);
                startX = (float) Math.cos(angle) * mCenterX * 0.5f;
                startY = (float) Math.sin(angle) * mCenterY * 0.15f;
                scale = 0.8f;
                // speed = 0.4210f;

            } else if (i == 8) {
                angle = (float) ((215) * Math.PI / 180);
                startX = (float) Math.cos(angle) * mCenterX * 0.8f;
                startY = (float) Math.sin(angle) * mCenterY * 0.35f;
                // speed = 0.5789f;

            } else if (i == 9) {
                angle = (float) ((225) * Math.PI / 180);
                startX = (float) Math.cos(angle) * mCenterX * 0.4f;
                startY = (float) Math.sin(angle) * mCenterY * 0.5f;
                // speed = 0.5789f;
            }
            Matrix matrix = new Matrix();
            StarModel model = new StarModel(mStar, angle, startX, startY, matrix, scale, speed);

            mStarModelList.add(model);
        }
    }

}
