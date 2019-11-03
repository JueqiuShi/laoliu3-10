package com.example.a98189.taxi.driver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.a98189.taxi.MainActivity;
import com.example.a98189.taxi.R;
import com.example.a98189.taxi.driver_client.Delete_dingdan;
import com.example.a98189.taxi.yunan.DriverActivity;

/**
 * 本次订单-司机
 */

public class Suceess_own_dingdan extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_own_dingdan);


        Button home = findViewById(R.id.home_success_dingdan);
        Button start = findViewById(R.id.start_driver);
        Button delete = findViewById(R.id.delete);

        //主界面具体实现
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回主界面
                Intent intent=new Intent(Suceess_own_dingdan.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        //开始发车具体实现
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogstart();
            }
        });
        //取消订单具体实现
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogdelete();
            }
        });

    }
    //发车按钮对话框
    private void showDialogstart(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.toolsbutton);
        builder.setTitle("温馨提示");
        builder.setMessage("请确认发车！");
        //左边Button
        builder.setNegativeButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //开车界面
                        Intent intent=new Intent(Suceess_own_dingdan.this, Start_driver.class);
                        startActivity(intent);
                        finish();
                    }
                    })
                //右边button
                .setPositiveButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }
    //取消订单对话框
    private void showDialogdelete(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.toolsbutton);
        builder.setTitle("温馨提示");
        builder.setMessage("是否取消订单！");
        //左边Button
        builder.setNegativeButton("是的",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除订单
                        Intent intent=new Intent(Suceess_own_dingdan.this, Delete_dingdan.class);
                        startActivity(intent);
                        finish();
                    }
                })
                //右边button
                .setPositiveButton("算了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //无操作
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }
}
