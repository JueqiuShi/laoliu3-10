package com.example.a98189.taxi.driver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.a98189.taxi.R;

import java.util.Timer;
import java.util.TimerTask;

public class Success_own extends Activity {
    private static final long DELAY = 3000;
    private TimerTask task;
    private int Time=3;
    TextView time;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if (msg.what==0) {
                if (Time>0) {
                    //时间--
                    Time--;
                    //给时间赋值
                    time.setText(Time+"秒后跳转页面......");
                    handler.sendEmptyMessageDelayed(0, 1000);
                }else {
                    Intent intent=new Intent(Success_own.this,Suceess_own_dingdan.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    };


    /**
     * 返回键禁用
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_own);

        Button button_dingdan = findViewById(R.id.success_own_choose);
        Button button_home = findViewById(R.id.success_own_home);
        time = findViewById(R.id.time);

        handler.sendEmptyMessageDelayed(0,1000);



        /**
         * 3秒后界面跳转
         */
        /*final Intent localIntent=new Intent(this,Suceess_own_dingdan.class);//你要转向的Activity
        Timer timer=new Timer();
        final TimerTask tast=new TimerTask() {
            @Override
            public void run(){
                startActivity(localIntent);//执行
                finish();
            }
        };
        //设置时间
        timer.schedule(tast,DELAY);//3秒后

         */
        //该次订单
        button_dingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 直接将Time变为0
                 * 跳转该单界面
                 */
                Time = 0;
//                Intent intent = new Intent(Success_own.this,Suceess_own_dingdan.class);
//                startActivity(intent);
                //取消定时跳转activity线程
//                tast.cancel();
                finish();
            }
        });

    }
}
