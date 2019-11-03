package com.example.a98189.taxi.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.a98189.taxi.MainActivity;
import com.example.a98189.taxi.R;

/**
 * 本次订单-乘客
 */

public class Success_client_dingdan extends Activity {
    //返回键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //点击完返回键，执行的动作
            //返回主界面
            Intent intent=new Intent(Success_client_dingdan.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_client_dingdan);
        Button home = findViewById(R.id.home_success_dingdan);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回主界面
                Intent intent=new Intent(Success_client_dingdan.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
