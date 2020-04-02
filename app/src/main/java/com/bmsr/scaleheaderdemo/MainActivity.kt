package com.bmsr.scaleheaderdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1.setOnClickListener {
            it.context.startActivity(Intent(it.context, ActivityC::class.java))
        }
        btn2.setOnClickListener {
            it.context.startActivity(Intent(it.context, ActivityB::class.java))
        }

    }
}
