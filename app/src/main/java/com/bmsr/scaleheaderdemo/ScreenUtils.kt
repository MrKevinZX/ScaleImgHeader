package com.bmsr.scaleheaderdemo

import android.app.Activity
import android.content.Context
import android.view.WindowManager

fun getScreenWidth(context: Activity?): List<Int> {
    val wm = context
        ?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val width = wm.defaultDisplay.width
    val height = wm.defaultDisplay.height
    return listOf(width, height)
}