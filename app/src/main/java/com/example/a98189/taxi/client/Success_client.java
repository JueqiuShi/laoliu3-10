package com.example.a98189.taxi.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.a98189.taxi.R;
import com.example.a98189.taxi.driver.Suceess_own_dingdan;

import java.util.Timer;
import java.util.TimerTask;

public class Success_client extends Activity {
    private static final long DELAY = 5000;
    private TimerTask task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_client);
        Button button_dingdan = findViewById(R.id.success_own_choose);
        Button button_home = findViewById(R.id.success_own_home);
        final Intent localIntent=new Intent(this, Suceess_own_dingdan.class);//你要转向的Activity
        Timer timer=new Timer();
        TimerTask tast=new TimerTask() {
            @Override
            public void run(){
                startActivity(localIntent);//执行
            }
        };
        timer.schedule(tast,DELAY);//10秒后
        button_dingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Success_client.this,Suceess_own_dingdan.class);
                startActivity(intent);
            }
        });

    }
}

