package com.bmsr.scaleheaderdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityH extends AppCompatActivity {
    private static final String TAG = "WDD-ActivityH";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "HeaderBehavior onTouchEvent = " + event.getAction());
        return super.onTouchEvent(event);
    }
}
