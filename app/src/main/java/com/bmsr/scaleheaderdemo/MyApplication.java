package com.bmsr.scaleheaderdemo;

import android.app.Application;
import android.content.Context;

import com.bun.miitmdid.core.JLibrary;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            JLibrary.InitEntry(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }
}
