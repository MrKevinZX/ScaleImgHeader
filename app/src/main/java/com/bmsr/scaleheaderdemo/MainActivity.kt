package com.bmsr.scaleheaderdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_c.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val screenWidth = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1.setOnClickListener {
            it.context.startActivity(Intent(it.context, ActivityC::class.java))
        }
        btn2.setOnClickListener {
            it.context.startActivity(Intent(it.context, ActivityB::class.java))
        }
        btn4.setOnClickListener {
            it.context.startActivity(Intent(it.context, ActivityD::class.java))
        }
        btn3.setOnClickListener {
            val height = viewpager.height +20
            val params = viewpager.layoutParams
            params.height = height
            viewpager.layoutParams = params
//            viewpager.layout(viewpager.left, 0,viewpager.right, viewpager.bottom + 20)
//              viewpager.measure(
//                  View.MeasureSpec.EXACTLY,
//                  View.MeasureSpec.EXACTLY)
        }
        val imgLists = mutableListOf<View>()
        val resIds = mutableListOf(R.mipmap.test,R.mipmap.test2,R.mipmap.test3,R.mipmap.test4)

        for (i in 0..3) {
            val img = ImageView(this)
            img.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            img.setImageResource(resIds[i])
            img.scaleType=ImageView.ScaleType.CENTER_CROP
            imgLists.add(img)
        }
        viewpager.adapter = TestImageAdapter(imgList = imgLists);
        viewpager.layoutParams.height = getScreenWidth(this)[0]

    }

    fun getScreenWidth(context: Context?): List<Int> {
        val wm = this
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width
        val height = wm.defaultDisplay.height
        return listOf(width, height)
    }
}
