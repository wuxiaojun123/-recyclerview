package com.example.myapplication;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

public class App extends Application {

    private static App	mApp;


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static App getApplication() {
        return mApp;
    }
}
