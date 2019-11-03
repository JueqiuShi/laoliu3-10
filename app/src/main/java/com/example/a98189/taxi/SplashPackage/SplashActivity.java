package com.example.a98189.taxi.SplashPackage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.a98189.taxi.FirstActivity;
import com.example.a98189.taxi.R;


/**
 * 过度页面：主要是为了选择跳转到引导页面和欢迎界面的
 */
public class SplashActivity extends Activity {

    //是否是第一次使用
    private boolean isFirstUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //不显示状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences preferences = getSharedPreferences("isFirstUse", MODE_PRIVATE );
        isFirstUse = preferences.getBoolean("isFirstUse", true);
        /**
         *如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
         */
        if (isFirstUse) {
            startActivity(new Intent(this, GuideActivtiy.class));
        } else {
            startActivity(new Intent(this, FirstActivity.class));
        }
        finish();
        //实例化Editor对象
        SharedPreferences.Editor editor = preferences.edit();
        //存入数据
        editor.putBoolean("isFirstUse", false);
        //提交修改
        editor.commit();
    }


}
