package com.bmsr.scaleheaderdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class CustomView extends RelativeLayout {
    private static final String TAG = "WDD-CustomView";
    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "HeaderBehavior onTouchEvent = " + event.getAction());
        return super.onTouchEvent(event);
    }
}
