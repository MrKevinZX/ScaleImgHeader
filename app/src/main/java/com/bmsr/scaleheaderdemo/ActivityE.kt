package com.bmsr.scaleheaderdemo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_e.*

class ActivityE :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e)
        initViewpager()
        initRecyclerview()
        initBehavior()
    }


    private fun initBehavior() {

        user_container.post {
            val params = user_container.layoutParams as CoordinatorLayout.LayoutParams
            params.behavior = CustomScrollBehavior1(getPullHeigth(), getPushHeigt(),
                object : ScaleListener {
                    override fun updateBannerSize(size: Int) {
                        val parmas = viewpager.layoutParams
                        parmas.height += size
                        viewpager.layoutParams = parmas
                    }

                    override fun updateBannerHeight(size: Float) {
                        val parmas = viewpager.layoutParams
                        parmas.height = size.toInt()
                        viewpager.layoutParams = parmas
                    }

                })
            user_container.layoutParams = params
        }


    }

    private fun getPushHeigt(): Int =  nav_container.height + header_container.height

    private fun getPullHeigth(): Int =getScreenWidth(this)[0]

    fun initViewpager() {
        val imgLists = mutableListOf<View>()
        val resIds = mutableListOf(R.mipmap.test,R.mipmap.test2,R.mipmap.test3,R.mipmap.test4)
        viewpager.layoutParams.height = getScreenWidth(this)[0]
        for (i in 0..3) {
            val img = ImageView(this)
            img.setImageResource(resIds[i])
            img.scaleType= ImageView.ScaleType.CENTER_CROP
            imgLists.add(img)
        }
        viewpager.adapter = TestImageAdapter(imgList = imgLists);
    }



    private fun initRecyclerview() {
        val list = mutableListOf("测试数据")
        for (i in 1..100) {
            list.add("测试数据---> " + i)
        }
        recycler_view.adapter = TestAdapter(list)
        recycler_view.layoutManager = LinearLayoutManager(this);
    }

    fun getScreenWidth(context: Context?): List<Int> {
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