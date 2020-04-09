package com.bmsr.scaleheaderdemo;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class BtnHavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "BtnHavior";
    private CustomViewPager viewpager;
    public BtnHavior() {
    }

    public BtnHavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        boolean res = super.onLayoutChild(parent, child, layoutDirection);
         child.setY(ScreenUtilsKt.getScreenWidth((Activity) child.getContext()).get(0));
         viewpager = parent.findViewById(R.id.viewpager);
         return res;
    }



    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        Log.i(TAG, "onStartNestedScroll: ");
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy < 0) {


            ViewCompat.offsetTopAndBottom(child, -dy);
            ViewGroup.LayoutParams params = viewpager.getLayoutParams();
            int height = viewpager.getHeight();
            viewpager.isScrolling = true;
            Log.i("BtnHavior", "onNestedPreScroll: dy = " + dy + ", height = " + height);
//            params.height = height -dy;
            viewpager.layout(viewpager.getLeft(), 0, viewpager.getRight(), viewpager.getBottom() - dy);

            consumed[1] = -dy;
            Log.i("BtnHavior", "onNestedPreScroll: dy = " + dy + ", height = " + viewpager.getHeight());
        } else {
            ViewCompat.offsetTopAndBottom(child, -dy);
            ViewGroup.LayoutParams params = viewpager.getLayoutParams();
            int height = viewpager.getHeight();
            viewpager.isScrolling = true;
            Log.i("BtnHavior", "onNestedPreScroll: dy = " + dy + ", height = " + height);
//            params.height = height -dy;
            viewpager.layout(viewpager.getLeft(), 0, viewpager.getRight(), viewpager.getBottom() - dy);

            consumed[1] = dy;
        }
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
        Log.i(TAG, "onStopNestedScroll: --->");
        viewpager.layout(viewpager.getLeft(), viewpager.getTop(), viewpager.getRight(), ScreenUtilsKt.getScreenWidth((Activity) child.getContext()).get(0));
        child.setY(ScreenUtilsKt.getScreenWidth((Activity) child.getContext()).get(0));
        viewpager.isScrolling = false;
    }
}
