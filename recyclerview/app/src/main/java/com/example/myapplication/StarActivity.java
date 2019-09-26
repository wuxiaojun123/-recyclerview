package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.utils.ScreenUtils;
import com.example.myapplication.view.star.StarLayout;

public class StarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);

    }



    public void startStar(View view) {
        FrameLayout decorView = (FrameLayout) getWindow().getDecorView();

        StarLayout starLayout = new StarLayout(this);
        starLayout.setOnAnimationEnd(new StarLayout.OnAnimationEnd() {
            @Override
            public void onAnimationEnd() {
                decorView.removeView(starLayout);
            }
        });
        decorView.addView(starLayout);

        int[] starInWindow = new int[2];
        starInWindow[0] = ScreenUtils.getScreenWidth();
        starInWindow[1] = ScreenUtils.getScreenHeight();
        starLayout.startRingAnim(starInWindow);

    }


}
