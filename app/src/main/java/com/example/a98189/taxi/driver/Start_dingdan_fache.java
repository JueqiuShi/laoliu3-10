package com.example.a98189.taxi.driver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.a98189.taxi.R;

/**
 * 开始前往目的地
 *
 * 需要实现功能
 * 1.全程录音
 * 2.地图路线显示
 * 3.一个完成订单按钮
 */
public class Start_dingdan_fache extends Activity implements View.OnClickListener {
    Button finish_dingdan;
    private  Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_driver_kaiche);
        init();
    }

    private void init() {
        finish_dingdan = findViewById(R.id.finish_dingdan);//完成订单按钮
        finish_dingdan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //完成订单具体实现
        if (v == finish_dingdan){
            intent = newIntent(Start_dingdan_fache.this,Finish_dingdan_driver.class);
            startActivity(intent);

        }
    }
    public static Intent newIntent(Context context, Class c) {
        Intent intent = new Intent(context, c);
        return intent;
    }
}
