package com.bmsr.scaleheaderdemo

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class TestImageAdapter(val imgList: MutableList<View>) : PagerAdapter() {
    lateinit var currentView : View
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int =imgList.size

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        currentView = `object` as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(imgList[position])
        return imgList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

}
