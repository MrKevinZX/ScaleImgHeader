package com.bmsr.scaleheaderdemo.banner;

import androidx.viewpager.widget.ViewPager;

import com.bmsr.scaleheaderdemo.banner.transformer.AccordionTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.BackgroundToForegroundTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.CubeInTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.CubeOutTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.DefaultTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.DepthPageTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.FlipHorizontalTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.FlipVerticalTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.ForegroundToBackgroundTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.RotateDownTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.RotateUpTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.ScaleInOutTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.StackTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.TabletTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.ZoomInTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.ZoomOutSlideTransformer;
import com.bmsr.scaleheaderdemo.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends ViewPager.PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
