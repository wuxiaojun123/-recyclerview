package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.customLayoutManager.CustomLayoutManagerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({ R.id.id_tv_custom_layoutmanager,R.id.id_tv_waterfall_flow,R.id.id_tv_star })
    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.id_tv_custom_layoutmanager:
                startActivity(new Intent(this,CustomLayoutManagerActivity.class));


                break;
            case R.id.id_tv_waterfall_flow:


                break;
            case R.id.id_tv_star:
                startActivity(new Intent(this,StarActivity.class));

                break;
        }
    }


}
