package com.example.a98189.taxi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a98189.taxi.R;

public class Activity_my_about extends Activity {
    ImageView icon_back;
    TextView gongneng;
    TextView tousu;
    TextView xieyi;
    TextView check_upData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_about);
        icon_back=findViewById(R.id.icon_back);
        gongneng=findViewById(R.id.gongneng);
        tousu=findViewById(R.id.tousu);
        xieyi=findViewById(R.id.xieyi);
        check_upData=findViewById(R.id.check_upData);
        gongneng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_my_about.this,About_gongnengjieshao.class);
                startActivity(intent);
                //Toast.makeText(AboutActivity.this, "功能介绍", Toast.LENGTH_SHORT).show();
            }
        });
        tousu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_my_about.this,About_tousu.class);
                startActivity(intent);

                //Toast.makeText(AboutActivity.this, "投诉", Toast.LENGTH_SHORT).show();
            }
        });
        xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_my_about.this,About_xieyi.class);
                startActivity(intent);
//                Toast.makeText(Activity_my_about.this, "用户协议", Toast.LENGTH_SHORT).show();
            }
        });
        check_upData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_my_about.this,About_update.class);
                startActivity(intent);
                //Toast.makeText(AboutActivity.this, "检查新版本", Toast.LENGTH_SHORT).show();
            }
        });
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_my_about.this.finish();
            }
        });
    }
}
