package com.bmsr.scaleheaderdemo

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewConfiguration
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class CustomScrollBehavior2 : CoordinatorLayout.Behavior<View> {
    var mPushHeigh = 0;
    var mPullHeight = 0
    lateinit var scaleListener : ScaleListener;
    lateinit var recyclerView : RecyclerView;
    var isReleaseAnimationRunning = false
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(pullHeight: Int, pushHeight : Int, listener: ScaleListener) {
        this.mPushHeigh = pushHeight
        this.mPullHeight = pullHeight;
        this.scaleListener = listener
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        val res =  super.onLayoutChild(parent, child, layoutDirection)
        recyclerView = parent.findViewById(R.id.recycler_view)
        child.y = mPullHeight.toFloat()
        return res
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean = axes == ViewCompat.SCROLL_AXIS_VERTICAL

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (dy > 0) {
            if (isPushTop(child)) {

            } else{
                child.y -= dy
                consumed[1] = dy
            }
        } else{
            if (!recyclerViewCanScroll()) {

            } else{
                if (isPullOrigin(child)) {
                    scaleListener.updateBannerSize(-dy)
                }
                child.y -= dy
                consumed[1] = -dy
            }
        }
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (dyUnconsumed < 0) {
            Log.i("wdd", "onNestedScroll dyUnconsumed = " + dyUnconsumed)
            if (type == ViewCompat.TYPE_TOUCH) {
                scroll(child, dyUnconsumed, 0, getMaxOffset(child))
            } else{
                if (!overScroller!!.computeScrollOffset()
                    || Math.abs(overScroller!!.currVelocity) < Math.abs(getMinFlingVelocity(child))
                    || getOffset(child) >= getmaxFlingOffset(child)
                ) {
                    Log.i("wdd", "stopNestedScroll currVelocity = " + overScroller!!.currVelocity + "" +
                            " getMinFlingVelocity = " + getMinFlingVelocity(child) +
                            " chily position = " + getOffset(child) + ", maxFlingOffset = " + getmaxFlingOffset(child))
                    ViewCompat.stopNestedScroll(target, ViewCompat.TYPE_NON_TOUCH)
                } else{
                    scroll(child, dyUnconsumed, getOffset(child), getmaxFlingOffset(child))
                }
            }


        } else if (dyUnconsumed > 0){
            if (type == ViewCompat.TYPE_TOUCH) {
                scroll(child, dyUnconsumed, mPushHeigh, 0)
            } else{
                if (overScroller == null ||
                        overScroller!!.computeScrollOffset() ||
                        Math.abs(overScroller!!.currVelocity) < Math.abs(getMinFlingVelocity(child)) ||
                        getOffset(child) <= getmaxFlingOffset(child)
                ) {
                    ViewCompat.stopNestedScroll(target, ViewCompat.TYPE_NON_TOUCH)
                } else {
                    scroll(child, dyUnconsumed,getmaxFlingOffset(child), getOffset(child))
                }
            }
        }
    }


    private fun getmaxFlingOffset(child: View): Int = (mPullHeight * 1.3).toInt()

    var mMinFlingVelocity = 0

    /**
     * 获得一个fling手势动作的最小速度值。
     */
    private fun getMinFlingVelocity(child: View): Int {
        if (mMinFlingVelocity <= 0) {
            mMinFlingVelocity = (ViewConfiguration.get(child.context).scaledMinimumFlingVelocity * 1.5).toInt()
        }
        Log.i("wdd", "getMinFlingVelocity = " + mMinFlingVelocity)
        return mMinFlingVelocity
    }

    private fun scroll(child: View, dyUnconsumed: Int, minOffset: Int, maxOffset: Int) {
        val newOffset = child.y - computerWithDampingFactor(child, dyUnconsumed)
        computerOffset(child, newOffset.toInt(), minOffset, maxOffset)
    }

    private fun computerOffset(child: View, newOffset: Int, minOffset: Int, maxOffset: Int): Int {
        Log.i("wdd", "computerOffset newOffset = " + newOffset + ", minOffset = " + minOffset + ", maxOffset = " + maxOffset)
        val curOffset = child.y.toInt()
        var consumed = 0
        if (curOffset >= minOffset && curOffset <= maxOffset) {
            val newOffset1 = MathUtils.clamp(newOffset, minOffset, maxOffset)
            if (curOffset != newOffset1) {
                child.y = newOffset1.toFloat()

                consumed = curOffset - newOffset1
            }
        }
        return consumed
    }
    private fun computerWithDampingFactor(child: View, dyUnconsumed: Int): Int {
        val absOffset = Math.abs(child.y)
        val progress = absOffset * 1f / mPullHeight

        var fator = 1 + 4 * progress
        if (fator == 0F ) {
            fator = 1F;
        }
        val newDistance = (dyUnconsumed / fator + 0.5f)
        return newDistance.toInt()
    }

    private fun getOffset(child: View): Int = child.y.toInt()

    private fun getMaxOffset(child: View): Int {
        return child.height
    }


    private fun isPullOrigin(child: View): Boolean = child.y >= mPullHeight

    private fun isPUllMax(child: View): Boolean = child.y > mPullHeight * 1.3

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        type: Int
    ) {

        if (type == ViewCompat.TYPE_TOUCH) {
            if (getOffset(child) > mPullHeight) {
                if (!overScroller!!.computeScrollOffset()) {
                    springBack(child)
                }
            }
        } else {
            if (getOffset(child) > mPullHeight) {
                springBack(child)
            }
        }

    }

    private fun springBack(child: View) {
        backToOriginPosition(child)
    }

    var overScroller : OverScroller? = null

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        handlePullFling(coordinatorLayout, child, velocityY)
        return false
    }
    lateinit var mFlingRunnable : Runnable

    class FlingRunnable : Runnable {
        var parent: CoordinatorLayout
        var child: View
        var overScroller : OverScroller
        constructor(parent: CoordinatorLayout, child: View, overScroller: OverScroller) {
            this.parent = parent
            this.child = child
            this.overScroller = overScroller
        }

        override fun run() {
            if (overScroller != null) {
                Log.i("wdd", "scroll Position = " + overScroller.currY + ", childY = " + child.y)
                if (overScroller.computeScrollOffset())  {
                    ViewCompat.postOnAnimation(child, this)
                }
            }
        }

    }

    private fun handlePullFling(parent: CoordinatorLayout, child: View, velocityY: Float)  {
        overScroller = OverScroller(child.context)
        overScroller!!.fling(0, 0,
            0, 0,
            0, 0,
            Int.MIN_VALUE, Int.MAX_VALUE
        )
    }

    private fun onFlingFinished(parent: CoordinatorLayout, child: View) {

    }

    private fun backToOriginPosition(child: View) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!isReleaseAnimationRunning) {
                isReleaseAnimationRunning = true
                val lstPosition = child.y
                Log.i("wdd", "backToOriginPosition= " )
                child.animate().translationY(mPullHeight.toFloat()).setDuration(200L).setUpdateListener {

                    val delta = lstPosition - ((lstPosition - mPullHeight) * it.animatedValue as Float)
                    scaleListener.updateBannerHeight(delta)
                    if (it.animatedValue == 1F) {
                        stopAnimation()
                    }
                }
            }

        } else{

        }
    }

    private fun stopAnimation() {
        isReleaseAnimationRunning = false
    }

    private fun isPushTop(child: View): Boolean {
        return child.y < mPushHeigh
    }

    private fun recyclerViewCanScroll(): Boolean {
        return recyclerView != null && !recyclerView.canScrollVertically(-1)
    }
}
