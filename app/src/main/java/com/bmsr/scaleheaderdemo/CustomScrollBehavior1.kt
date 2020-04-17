package com.bmsr.scaleheaderdemo

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class CustomScrollBehavior1 : CoordinatorLayout.Behavior<View> {
    var mPushHeigh = 0;
    var mPullHeight = 0
    lateinit var scaleListener : ScaleListener;
    lateinit var recyclerView : RecyclerView;
    var isReleaseAnimationRunning = false
    var lastRawY = 0;
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
        overScroller = OverScroller(child.context)
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
        val childY = child.y
        if (dy > 0) {
            if (child.y > mPullHeight) {
                zoomBanner(child, dy)
                consumed[1] = dy
            } else if (child.y  >= mPushHeigh) {
                val delta: Float = Math.max(childY - dy, mPushHeigh.toFloat())
                child.y = delta
                consumed[1] = (childY - delta).toInt()
            }
        } else{
            if (!recyclerViewCanScroll()) {

            } else{
                if (childY < mPullHeight) {
                    val delta = Math.min(mPushHeigh.toFloat(), childY - dy).toInt()
                    child.setY(childY - dy)
                    consumed[1] = -dy
                }
                if (type == ViewCompat.TYPE_TOUCH && childY >= mPullHeight) {
                    zoomBanner(child, dy)
                    consumed[1] = -dy
                }
            }
        }
    }

    private fun zoomBanner(child: View, dy: Int) {
        scaleListener.updateBannerSize(-dy)
        child.y -= dy
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
            Log.i("wdd", "childY = " + child.y)
            if (child.y > mPullHeight * 1.7 ||
                overScroller!!.currVelocity <=  getMinFlingVelocity(child)) {
                ViewCompat.stopNestedScroll(child, ViewCompat.TYPE_NON_TOUCH)
            } else{
                val res = scroll(child, dyUnconsumed, (mPullHeight * 1.4).toInt());
                consumed[1] += dyUnconsumed - res
            }
        } else{
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
        }

    }

    private fun scroll(child: View, dyUnconsumed: Int, maxFlingOffset: Int) :Int{
        val newOffset = child.y - computerWithDampingFactor(child, dyUnconsumed)
        val res = computerOffset(child, newOffset.toInt(), mPullHeight, maxFlingOffset)
        Log.i("wdd", "computerOffset newOffset = " + newOffset +
                ", minOffset = " + mPullHeight + ", maxOffset = " + maxFlingOffset +
                ", currVelocity = " + overScroller!!.currVelocity +
                ", MinFlingVelocity = " + getMinFlingVelocity(child) +
                ", res = " + res + ", dyUnconsumed = " + dyUnconsumed
        )
        return res
    }

    private fun computerOffset(child: View, newOffset: Int, minOffset: Int, maxOffset: Int): Int {

        val curOffset = child.y.toInt()
        var consumed = 0
        if (curOffset >= minOffset && curOffset <= maxOffset) {
            val newOffset1 = MathUtils.clamp(newOffset, minOffset, maxOffset)
            if (curOffset != newOffset1) {
                child.y = newOffset1.toFloat()
                scaleListener.updateBannerSize(newOffset1)
                consumed = curOffset - newOffset1
                Log.i("wdd", "consumed1 = " + consumed)
                return consumed
            }
        }
        return 0
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

    var mMinFlingVelocity = 0

    /**
     * 获得一个fling手势动作的最小速度值。
     */
    private fun getMinFlingVelocity(child: View): Int {
        if (mMinFlingVelocity <= 0) {
            mMinFlingVelocity = ViewConfiguration.get(child.context).scaledMinimumFlingVelocity * 10
        }
        Log.i("wdd", "getMinFlingVelocity = " + mMinFlingVelocity)
        return mMinFlingVelocity
    }


    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        type: Int
    ) {

        if (child.y > mPullHeight) {
            backToOriginPosition(child)
        }

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

    private fun handlePullFling(parent: CoordinatorLayout, child: View, velocityY: Float)  {

        Log.i("wdd", "handlePullFling velocityY = " + velocityY
                + ", childY = " + 0
                + ", MinY = " + (mPullHeight * 1.2).toInt()
                + ", MaxY = " + (mPullHeight * 1.5))

        overScroller!!.fling(0, 0,
            0, velocityY.toInt(),
            0, 0,
            Int.MIN_VALUE, Int.MAX_VALUE,
            0, 100
        )


    }

    private fun backToOriginPosition(child: View) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!isReleaseAnimationRunning) {
                isReleaseAnimationRunning = true
                val lstPosition = child.y

                child.animate().translationY(mPullHeight.toFloat()).setDuration(600L).setUpdateListener {

                    val delta = lstPosition - ((lstPosition - mPullHeight) * it.animatedValue as Float)
                    Log.i("wdd", "backToOriginPosition= delta = " + delta.toInt() + ", childY = " + child.y )
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

    override fun onTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        return handleMoveChild(child, ev) || super.onTouchEvent(parent, child, ev)
    }

    private fun handleMoveChild(child: View, ev: MotionEvent): Boolean {

        var res = false
        when(ev.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.i("wdd", "ev rawY = " + ev.rawY + " childY = " + child.y)
                if (ev.rawY < child.y) {
                    res = true
                }
                recordPosition(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                moveChild(child, ev)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                 if (child.y > mPullHeight) {
                     backToOriginPosition(child)
                 }
            }
            else -> {

            }
        }
        return res
    }

    private fun moveChild(child: View, ev: MotionEvent) {
        val  delta : Int = (ev.rawY - lastRawY).toInt()
        if (child.y > mPullHeight) {
            scaleListener.updateBannerSize(delta)
        }
        child.y += delta

        lastRawY = ev.rawY.toInt()
    }

    private fun recordPosition(ev: MotionEvent) {
        lastRawY = ev.rawY.toInt()
    }
}
