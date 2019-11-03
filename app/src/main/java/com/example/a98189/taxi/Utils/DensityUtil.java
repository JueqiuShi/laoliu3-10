package com.example.a98189.taxi.Utils;

import android.content.Context;

/**
 * Created by  liangjiachneg.Date: 2019/3/13.Time: 9:16
 * <p>
 * 这个类的作用是：单位转换工具
 */
public class DensityUtil {
    /** 为了适应手机屏幕分辨率不同导致的布局显示有所不同
     * px和dp互相转换工具
     * */
    public static int dip2px(Context context , float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
    /**
     * 根据手机的分辨率从px（像素）转换成dp
     * */
    public static int px2dip(Context context ,float pxValue){
        final float scale  = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }
}

