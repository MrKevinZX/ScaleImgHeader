package com.bmsr.scaleheaderdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager  extends ViewPager {
    public boolean isScrolling;
    private static final String TAG = "CustomViewPager";

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: widthMeasureSpec = + " + widthMeasureSpec + ", heightMeasureSpec = " + heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        if (isScrolling) {
            TestImageAdapter adapter = (TestImageAdapter) getAdapter();
            adapter.getCurrentView().layout(l, t, r, b);
        } else {
            super.onLayout(changed, l, t, r, b);
        }
        Log.i(TAG, "onLayout: b =" + Log.getStackTraceString(new Throwable()));
    }


}
