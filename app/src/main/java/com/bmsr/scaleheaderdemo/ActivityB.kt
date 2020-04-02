package com.bmsr.scaleheaderdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_b.*
import kotlinx.android.synthetic.main.activity_main.*

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
        recycler_view.post{
            params.behavior = RecyclerBehavior(header_container.height, banner_container.height);
            recycler_view.layoutParams = params
        }
        val navParmas = header_container.layoutParams as CoordinatorLayout.LayoutParams
        header_container.post {
            navParmas.behavior = NavgationBarBehavior()
            header_container.layoutParams = navParmas
        }

    }

}