package com.example.myapplication.view.star;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class StarLayout extends FrameLayout {

    private ImageView idIvBigStar;			// 大星星

    private RingView	idRingView;				// 圆环

    private StarView	idSmallStarListView;	// 所有的小星星

    public StarLayout(@NonNull Context context) {
        this(context, null);
    }

    public StarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_star, this);

        idIvBigStar = findViewById(R.id.id_iv_big_star);
        idRingView = findViewById(R.id.id_ringview);
        idSmallStarListView = findViewById(R.id.id_starview);
    }

    public void startRingAnim(final int[] originLocation) {
        final int[] location = new int[2];
        idIvBigStar.post(new Runnable() {

            @Override public void run() {
                idIvBigStar.getLocationInWindow(location);

                final int disX = originLocation[0] - location[0];
                final int disY = originLocation[1] - location[1];

                idIvBigStar.setAlpha(0f);
                idRingView.startAnim(400);
                startBigStarScaleAnim(1200, 0).start();

                idSmallStarListView.startAnimation(10, 1600, 300);
                startBitStarTransAndScaleAnim(disX, disY, 1000, 1100);
            }
        });
    }

    private Animator startBigStarScaleAnim(int duration, int startDelay) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 5f, 8f);

        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override public void onAnimationStart(Animator animation) {
                idIvBigStar.setAlpha(1f);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                idIvBigStar.setScaleX(value);
                idIvBigStar.setScaleY(value);
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.setStartDelay(startDelay);
        return valueAnimator;
    }

    /***
     * 平移并且缩放动画
     */
    private void startBitStarTransAndScaleAnim(final int disX, final int disY, int duration, int delay) {
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(8f, 1f);
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                idIvBigStar.setScaleX(value);
                idIvBigStar.setScaleY(value);
            }
        });

        ValueAnimator transAnim = ValueAnimator.ofFloat(0, 1f);
        transAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                idIvBigStar.setTranslationX(value * disX);
                idIvBigStar.setTranslationY(value * disY);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnim).with(transAnim);
        animatorSet.setDuration(duration);
        animatorSet.setStartDelay(delay);
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override public void onAnimationEnd(Animator animation) { // 动画执行完毕
                if (onAnimationEnd != null) {
                    onAnimationEnd.onAnimationEnd();
                }
            }
        });
        animatorSet.start();
    }

    private OnAnimationEnd onAnimationEnd;

    public void setOnAnimationEnd(OnAnimationEnd onAnimationEnd) {
        this.onAnimationEnd = onAnimationEnd;
    }

    public interface OnAnimationEnd {

        void onAnimationEnd();
    }

}