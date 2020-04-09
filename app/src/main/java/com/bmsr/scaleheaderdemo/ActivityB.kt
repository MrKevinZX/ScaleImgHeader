package com.bmsr.scaleheaderdemo

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_b.*

class ActivityB :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        val list = mutableListOf("测试数据")
        for (i in 1..40) {
            list.add("测试数据---> " + i)
        }
        recycler_view.adapter = TestAdapter(list)
        recycler_view.layoutManager = LinearLayoutManager(this);
        val params : CoordinatorLayout.LayoutParams = recycler_view.layoutParams as CoordinatorLayout.LayoutParams

        val navParmas = header_container.layoutParams as CoordinatorLayout.LayoutParams
        header_container.post {
            navParmas.behavior = NavgationBarBehavior()
            header_container.layoutParams = navParmas
        }

        recycler_view.post{
            params.behavior = CustomScrollBehavior(
                getScreenWidth(ActivityB@this)[0],
                getScreenWidth(ActivityB@this)[0]
            );
            recycler_view.layoutParams = params
        }
        showBanner()

    }

    private fun showBanner() {
        val resIds = mutableListOf("https://img95.699pic.com/photo/40011/0709.jpg_wh860.jpg",
            "https://img95.699pic.com/photo/50046/5562.jpg_wh300.jpg",
            "https://pic4.zhimg.com/v2-3be05963f5f3753a8cb75b6692154d4a_1200x500.jpg",
            "https://pic-bucket.ws.126.net/photo/0009/2019-12-10/F02FNPP40AI20009NOS.jpg")
        banner.layoutParams.height = getScreenWidth(ActivityB@this)[0]
        banner.setImages(resIds)
            .setImageLoader(object : MyImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                if (imageView != null) {
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    Glide.with(this@ActivityB).load(path).centerCrop().into(imageView)
                }
            }
            }).isAutoPlay(false).start()

//        ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
//        layoutParams.height = mDeviceWidth;
//        banner.setLayoutParams(layoutParams);
        val indicator = banner.getIndicator()
        if (indicator != null) {
            val indicatorLayoutParams =
                indicator.layoutParams as RelativeLayout.LayoutParams
            indicatorLayoutParams.bottomMargin = dip2px(this, 10f)
            indicator.layoutParams = indicatorLayoutParams
        }
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