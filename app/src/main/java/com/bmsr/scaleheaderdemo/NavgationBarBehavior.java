package com.bmsr.scaleheaderdemo;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class NavgationBarBehavior extends CoordinatorLayout.Behavior<View> {
    private float mLastPostition = -1f;
    private static final int ALPHA_THRESHOLD = 100;//透明度变化阈值
    private int startAlphaPosition = 0; // 开始变化的位置
    private ImageView mBackIcon;
    public NavgationBarBehavior() {
    }

    public NavgationBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        boolean res =  super.onLayoutChild(parent, child, layoutDirection);
        mBackIcon = child.findViewById(R.id.iv_back);
        return res;
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
            startAlphaPosition = ALPHA_THRESHOLD + child.getHeight();
            mLastPostition = dependency.getY();
        }
        if (currentY > startAlphaPosition) {
            //不做任何事情
            child.setAlpha(0);
            //todo 跳转颜色
//            mBackIcon.setColorFilter(1, PorterDuff.Mode.SRC_IN);
        }

        if (currentY < startAlphaPosition) {
            //开始变化透明度
            child.setAlpha(1- ((currentY - child.getHeight()) / ALPHA_THRESHOLD));
        }
        if (currentY == child.getHeight()) {
            //完全改变透明度
        }
        return true;
    }
}
