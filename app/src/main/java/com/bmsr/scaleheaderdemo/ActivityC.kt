package com.bmsr.scaleheaderdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_c.*
import kotlinx.android.synthetic.main.activity_main.*

class ActivityC :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        val list = mutableListOf("测试数据")
        for (i in 1..40) {
            list.add("测试数据---> " + i)
            Log.i("wdd", "list data " + list[i])
        }
        recycler_view.adapter = TestAdapter(list)
        recycler_view.layoutManager = LinearLayoutManager(this);

    }
}