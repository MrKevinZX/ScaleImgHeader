package com.bmsr.scaleheaderdemo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_d.recycler_view
import kotlinx.android.synthetic.main.activity_d.viewpager

class ActivityD :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d)

        val imgLists = mutableListOf<View>()
        val resIds = mutableListOf(R.mipmap.test,R.mipmap.test2,R.mipmap.test3,R.mipmap.test4)

        for (i in 0..3) {
            val img = ImageView(this)
            img.setImageResource(resIds[i])
            img.scaleType= ImageView.ScaleType.CENTER_CROP
            imgLists.add(img)
        }
        viewpager.adapter = TestImageAdapter(imgList = imgLists);
        viewpager.layoutParams.height = getScreenWidth()[0]
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    Log.i("wdd", "onPageScrollStateChanged state = " + state)
                    recycler_view.y = getScreenWidth().get(0).toFloat()
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }

        })
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val list = mutableListOf("测试数据")
        for (i in 1..40) {
            list.add("测试数据---> " + i)
        }
        recycler_view.adapter = TestAdapter(list)
        recycler_view.layoutManager = LinearLayoutManager(this);
        val params : CoordinatorLayout.LayoutParams = recycler_view.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = BtnHavior()
    }

    fun getScreenWidth(): List<Int> {
        val wm = this
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width
        val height = wm.defaultDisplay.height
        return listOf(width, height)
    }


    fun dip2px(context: Context?, dpValue: Float): Int {
        return if (context == null) {
            0
        } else {
            val scale = context.resources.displayMetrics.density
            (dpValue * scale + 0.5f).toInt()
        }
    }

}