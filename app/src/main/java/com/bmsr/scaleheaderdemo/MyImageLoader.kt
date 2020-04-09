package com.bmsr.scaleheaderdemo

import android.content.Context
import android.widget.ImageView
import com.bmsr.scaleheaderdemo.banner.loader.ImageLoaderInterface

public abstract class MyImageLoader : ImageLoaderInterface <ImageView> {

    override fun createImageView(context: Context?): ImageView {
        val img = ImageView(context)
        img.scaleType = ImageView.ScaleType.FIT_XY
       return img
    }

}