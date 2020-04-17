package com.bmsr.scaleheaderdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bmsr.scaleheaderdemo.banner.view.BannerViewPager;

/**
 * header滑动拉伸
 */
public class CustomScrollBehavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "RecyclerBehavior";
    private int pushPosition; //上推终点的位置
    private int pullPosition;//下拉的最大位置
    private RecyclerView recyclerView;
    private ViewGroup banner;
    private ImageView mBannerStub;
    private ValueAnimator valueAnimator;
    private ViewGroup viewpager;
    private static final float MAX_ZOOM_HEIGHT = 1000;//放大最大高度
    private float mTotalDy;//手指在Y轴滑动的总距离
    private float mScaleValue;//图片缩放比例
    private int mImageViewHeight;//记录ImageView原始高度
    private boolean isAnimate;//是否做动画标志
    private int mLastBottom;//Appbar的变化高度
    private int mAppbarHeight;//记录AppbarLayout原始高度
    public CustomScrollBehavior(int pushPosition, int pullPosition) {
        this.pushPosition = pushPosition;
        this.pullPosition = pullPosition;
    }

    public CustomScrollBehavior() {
    }

    public CustomScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        boolean res = super.onLayoutChild(parent, child, layoutDirection);
        initView(parent);
        return res;
    }

    private void initView(View container) {
        recyclerView = container.findViewById(R.id.recycler_view);
        banner = container.findViewById(R.id.banner);
        mBannerStub = container.findViewById(R.id.banner_stub);
        viewpager = container.findViewById(R.id.bannerViewPager);
        mImageViewHeight = banner.getHeight();
        recyclerView.setY(pullPosition);
        ViewGroup.LayoutParams params = mBannerStub.getLayoutParams();
        params.height = mImageViewHeight;
        mBannerStub.setLayoutParams(params);
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
        isAnimate = true;
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
//                child.setY(delta);
                int diff = childY-delta;
                Log.i(TAG, "onNestedPreScroll: -->" + diff);
                ViewCompat.offsetTopAndBottom(child, childY-delta);
                consumed[1] = diff;
            }

        }
        if (dy < 0) {
            //img 拉伸
//            BannerViewPager.isScrolling = true;

            if (mBannerStub.getVisibility() == View.GONE) {
                showStub();
            }
            mBannerStub.layout(mBannerStub.getLeft(), mBannerStub.getTop(), mBannerStub.getRight(), mBannerStub.getBottom() - dy);
            child.offsetTopAndBottom(-dy);
            consumed[1] = -dy;
//            banner.layout(banner.getLeft(), banner.getTop(), banner.getRight(), banner.getBottom() - dy);
//            viewpager.layout(viewpager.getLeft(), viewpager.getTop(), viewpager.getRight(), viewpager.getBottom() - dy);

//            mBannerView.requestLayout();
//            child.setY(childY - dy);
//            int height1 = child.getHeight();
//            ViewGroup.LayoutParams params1 = child.getLayoutParams();
//            params1.height = height1 - 1;
//            child.setLayoutParams(params1);
//            int height = banner.getHeight();
//            ViewGroup.LayoutParams params = banner.getLayoutParams();
//            params.height = height + 1;
//            banner.setLayoutParams(params);


        }
    }

    private void showStub() {
        viewpager.setDrawingCacheEnabled(true);
        viewpager.buildDrawingCache();
        Bitmap bitmap = viewpager.getDrawingCache();
        mBannerStub.setImageBitmap(bitmap);
        mBannerStub.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mBannerStub.setVisibility(View.VISIBLE);
        banner.setVisibility(View.GONE);
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
//        recovery(child);
//        BannerViewPager.isScrolling = false;
        mBannerStub.setVisibility(View.GONE);
        banner.setVisibility(View.VISIBLE);
        mBannerStub.setImageBitmap(null);
        recyclerView.setY(mImageViewHeight);
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }


    private void zoomHeaderImageView(View child, int dy) {
        mTotalDy += -dy;
        mTotalDy = Math.min(mTotalDy, MAX_ZOOM_HEIGHT);
        mScaleValue = Math.max(1f, 1f + mTotalDy / MAX_ZOOM_HEIGHT);
        ViewGroup.LayoutParams params1 = child.getLayoutParams();
//        params1.height
        mLastBottom = mAppbarHeight + (int) (mImageViewHeight / 2 * (mScaleValue - 1));
        int height = banner.getHeight();
//        ViewGroup.LayoutParams params = mBannerView.getLayoutParams();
//        params.height = height + 10;
//        mBannerView.setLayoutParams(params);
        if (viewpager != null) {
//            mImgContainer.setBottom(mLastBottom);
        }

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
                valueAnimator = ValueAnimator.ofFloat(mScaleValue, 1f).setDuration(600);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        banner.setScaleX(value);
                        banner.setScaleY(value);
                        float position = mLastBottom - (mLastBottom - mAppbarHeight) * animation.getAnimatedFraction();
                        if (viewpager != null) {
                            viewpager.setBottom((int) position);
                        }
                        child.setY(position);
                    }
                });
                valueAnimator.start();
            } else {
                banner.setScaleX(1f);
                banner.setScaleY(1f);
                if (viewpager != null) {
                    viewpager.setBottom(mAppbarHeight);
                }
                child.setY(pullPosition);
            }
        }
    }
}
