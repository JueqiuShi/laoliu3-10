package com.example.a98189.taxi.activity;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a98189.taxi.Login.LoginActivity;
import com.example.a98189.taxi.MainActivity;
import com.example.a98189.taxi.R;
import com.example.a98189.taxi.Utils.ClassSavePersonData;
import com.example.a98189.taxi.Utils.GlobleVariable;
import com.example.a98189.taxi.fragment.MyFragment;

import static com.example.a98189.taxi.FirstActivity.newIntent;

public class Activity_my_info extends Activity implements View.OnClickListener {
    private ImageView toolbarIvBack;
    private TextView toolbarIvTitle;
    private ImageView toolbarIvRight;
    private TextView toolbarTvRight;
    private LinearLayout layLlHead;
    private ImageView ivHead;
    private LinearLayout layLlNickName;
    private TextView tvNickName;
    private LinearLayout layLlSex;
    private TextView tvSex;
    private LinearLayout layLlAddress;
    private LinearLayout layLlPersonalAuthentication;
    private TextView tvPersonalAuthentication;
    private LinearLayout layLlCompanyAuthentication;
    private TextView tvCompanyAuthentication;
    private Button exit_login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);


        toolbarIvBack = (ImageView)findViewById( R.id.toolbar_iv_back );//返回键
        toolbarIvTitle = (TextView)findViewById( R.id.toolbar_iv_title );//标题
        toolbarIvRight = (ImageView)findViewById( R.id.toolbar_iv_right );
        toolbarTvRight = (TextView)findViewById( R.id.toolbar_tv_right );
        layLlHead = (LinearLayout)findViewById( R.id.lay_ll_head );//头像行布局
        ivHead = (ImageView)findViewById( R.id.iv_head );//头像
        layLlNickName = (LinearLayout)findViewById( R.id.lay_ll_nick_name );//昵称行布局
        tvNickName = (TextView)findViewById( R.id.tv_nick_name );//设置昵称
        layLlSex = (LinearLayout)findViewById( R.id.lay_ll_sex );//性别行布局
        tvSex = (TextView)findViewById( R.id.tv_sex );//设置性别
        layLlAddress = (LinearLayout)findViewById( R.id.lay_ll_address );
        layLlPersonalAuthentication = (LinearLayout)findViewById( R.id.lay_ll_personal_authentication );//班级行布局
        tvPersonalAuthentication = (TextView)findViewById( R.id.tv_personal_authentication );//设置班级
        layLlCompanyAuthentication = (LinearLayout)findViewById( R.id.lay_ll_company_authentication );//手机号码行布局
        tvCompanyAuthentication = (TextView)findViewById( R.id.tv_company_authentication );//设置手机号码
        exit_login = (Button)findViewById( R.id.exit );//退出登录键

        exit_login.setOnClickListener( this );
        toolbarIvBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if ( v == exit_login ) {
            //删除本地保存的文件
//            ClassSavePersonData classSavePersonData   =  new ClassSavePersonData(GlobleVariable.UID, "Person", GlobleVariable.UID + ".txt");
//            classSavePersonData.cleanOldFile(GlobleVariable.UID + ".txt");

           //清除登录数据
            SharedPreferences sharedPreferences = this.getSharedPreferences("isLogin", MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();

            Intent intent = newIntent(Activity_my_info.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
            startActivity(intent);
            finish();
            // Handle clicks for exit
        }
        if (v == toolbarIvBack){
            Activity_my_info.this.finish();
//            Intent intent = newIntent(Activity_my_info.this, MainActivity.class);
//            //缺少一条让其返回主页fragement
//            startActivity(intent);
        }
        if(v == layLlHead){
            //头像activity
        }
        if(v == layLlNickName){
            //昵称activity

        }
        if(v == layLlSex){
            //性别activity

        }
        if(v == layLlPersonalAuthentication){
            //班级activity
        }
        if(v == layLlCompanyAuthentication){
            //手机号码activity
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
