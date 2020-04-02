package com.bmsr.scaleheaderdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class NavgationBarBehavior extends CoordinatorLayout.Behavior<View> {
    private float mLastPostition = -1f;
    private static final int AA= 100;
    private int startAlphaPosition = 0; // 开始变化的位置
    private int endAlphaPosition; //
    public NavgationBarBehavior() {
    }

    public NavgationBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency.getId() == R.id.recycler_view;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        float currentY = dependency.getY();
        if (startAlphaPosition == 0 ) {
            startAlphaPosition = AA + child.getHeight();
            mLastPostition = dependency.getY();
        }
        float delta = currentY - mLastPostition;
        if (currentY > startAlphaPosition) {
            //不做任何事情
            child.setAlpha(0);
        }

        if (currentY < startAlphaPosition) {
            //开始变化透明度
            child.setAlpha(1- ((currentY - child.getHeight()) / AA));
        }
        if (currentY == child.getHeight()) {
            //完全改变透明度

        }
        return true;
    }
}
