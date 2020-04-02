package com.bmsr.scaleheaderdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

public class RecyclerBehavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "WDD";
    private int pushPosition; //上推终点的位置
    private int pullPosition;//下拉的最大位置
    RecyclerView recyclerView;
    private ImageView mImageView;
    private ValueAnimator valueAnimator;
    private RelativeLayout mImgContainer;
    private static final float MAX_ZOOM_HEIGHT = 1000;//放大最大高度
    private float mTotalDy;//手指在Y轴滑动的总距离
    private float mScaleValue;//图片缩放比例
    private int mImageViewHeight;//记录ImageView原始高度
    private boolean isAnimate;//是否做动画标志
    private int mLastBottom;//Appbar的变化高度
    private int mAppbarHeight;//记录AppbarLayout原始高度
    public RecyclerBehavior(int pushPosition, int pullPosition) {
        this.pushPosition = pushPosition;
        this.pullPosition = pullPosition;
    }

    public RecyclerBehavior() {
    }

    public RecyclerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        boolean res = super.onLayoutChild(parent, child, layoutDirection);
        initView(parent);
        return res;
    }

    private void initView(View child) {
        recyclerView = child.findViewById(R.id.recycler_view);
        mImageView = child.findViewById(R.id.img_bg);
        mImgContainer = child.findViewById(R.id.banner_container);
        mAppbarHeight = mImgContainer.getHeight();
        mImageViewHeight = mImageView.getHeight();
        recyclerView.setY(pullPosition);
//        ViewCompat.offsetTopAndBottom(recyclerView, pullPosition);
    }
    /**
     * recycle 滑动到顶部
     * @return
     */
    protected boolean isBottomScrollTop() {
        //不可以可以纵向向上滑动， 表示滑动到顶部
        return recyclerView != null && !recyclerView.canScrollVertically(-1);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        int childY = (int) child.getY();

        if (dy > 0) {
            //上推
            if (childY > pullPosition) {
                zoomHeaderImageView(child, dy);
                consumed[1] = dy;
            } else if (childY > pushPosition) {
                int delta = Math.max(childY - dy, pushPosition);
                child.setY(delta);
                consumed[1] = childY - delta;
            }

        }
        if (dy < 0) {
            if (!isBottomScrollTop()) {
                //滑动到第一个item可见
            } else {
                if (childY < pullPosition) {
                    //滑动到最大位置
                    int delta = Math.min(pullPosition, childY - dy);
                    child.setY(delta);
                    consumed[1] = childY-delta;
                }

            }
            Log.i(TAG, "onNestedPreScroll: pull = " + pullPosition + ", childy =" + child.getY());
            if (type == ViewCompat.TYPE_TOUCH && childY >= pullPosition) {
                //img 拉伸
                zoomHeaderImageView(child, dy);
            }
        }
    }



    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        if (velocityY > 100) {
            isAnimate = false;
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull View child, View target, int type) {
        recovery(child);
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }


    private void zoomHeaderImageView(View child, int dy) {
        mTotalDy += -dy;
        mTotalDy = Math.min(mTotalDy, MAX_ZOOM_HEIGHT);
        mScaleValue = Math.max(1f, 1f + mTotalDy / MAX_ZOOM_HEIGHT);
        ViewCompat.setScaleX(mImageView, mScaleValue);
        ViewCompat.setScaleY(mImageView, mScaleValue);
        mLastBottom = mAppbarHeight + (int) (mImageViewHeight / 2 * (mScaleValue - 1));
        Log.i(TAG, "mLastBottom = " + mLastBottom);
        mImgContainer.setBottom(mLastBottom);
        child.setY(mLastBottom);
    }

    /**
     * 通过属性动画的形式，恢复AppbarLayout、ImageView的原始状态
     *
     * @param child
     */
    private void recovery(final View child) {
        if (mTotalDy > 0) {
            mTotalDy = 0;
            if (isAnimate) {
                valueAnimator = ValueAnimator.ofFloat(mScaleValue, 1f).setDuration(220);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        ViewCompat.setScaleX(mImageView, value);
                        ViewCompat.setScaleY(mImageView, value);
                        float position = mLastBottom - (mLastBottom - mAppbarHeight) * animation.getAnimatedFraction();
                        mImgContainer.setBottom((int) position);
                        child.setY(position);
                    }
                });
                valueAnimator.start();
            } else {
                ViewCompat.setScaleX(mImageView, 1f);
                ViewCompat.setScaleY(mImageView, 1f);
                mImgContainer.setBottom(mAppbarHeight);
                child.setY(pullPosition);
            }
        }
    }
}
