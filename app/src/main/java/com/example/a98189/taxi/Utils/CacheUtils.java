package com.example.a98189.taxi.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by  liangjiachnegAndroid Studio.Date: 2019/3/13.Time: 8:54
 * <p>
 * 这个类的作用是： 缓存类软件的参数和数据
 */
public class CacheUtils {
    //得到缓存值
    public  static  boolean getBoolean(Context context , String key){
        SharedPreferences sp = context.getSharedPreferences("WeChat",Context.MODE_PRIVATE);
        return  sp.getBoolean(key,false);
    }

    /**保存曾经进入主页面
     * @param context 上下文
     * @param key 键
     * @param value 值
     */

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("WeChat",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
}
