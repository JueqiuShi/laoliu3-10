package com.example.a98189.taxi.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a98189.taxi.MainActivity;
import com.example.a98189.taxi.Utils.Util;
import com.example.a98189.taxi.Zhifu;
import com.example.a98189.taxi.activity.About_update;
import com.example.a98189.taxi.activity.Activity_Feedback;
import com.example.a98189.taxi.activity.Activity_my_about;
import com.example.a98189.taxi.activity.Activity_my_info;
import com.example.a98189.taxi.activity.Activity_my_jifen;
import com.example.a98189.taxi.base.BaseFragment;
import com.example.a98189.taxi.R;
import com.example.a98189.taxi.view.CircleImageView;
import com.wyp.avatarstudio.AvatarStudio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by  liangjiachneg.Date: 2019/9/28.Time: 9:50
 * <p>
 * 这个类的作用是：我的页面
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {
    //auto
    private LinearLayout llHeaderBg;
    private CircleImageView touxiang;
    private TextView tvUsername;
    private TextView tvLevel;
    private LinearLayout llPersonalCenter;
    private LinearLayout llMyJifen;
    private LinearLayout llLastInformation;
    private LinearLayout llSkin;
    private LinearLayout llNightMode;
    private LinearLayout llAdvise;
    private LinearLayout llAboutUs;
//    private TextView textView;
    private LinearLayout llCheck;
//    private TextView textCheck;
    private  Intent intent;


    private static final String TAG = MyFragment.class.getSimpleName();//"DriverFragment"

    @Override
    protected View initView() {
        Log.e(TAG, "其他Fragment页面被初始化了...");
        View view = View.inflate(mContext, R.layout.fragment_my, null);
        /**
         * Find the Views in the layout<br />
         * <br />
         * Auto-created on 2019-10-09 22:02:29 by Android Layout Finder
         * (http://www.buzzingandroid.com/tools/android-layout-finder)
         */
        llHeaderBg = (LinearLayout) view.findViewById(R.id.ll_header_bg);//头部线性布局
        touxiang = (CircleImageView) view.findViewById(R.id.touxiang);//头像
        tvUsername = (TextView) view.findViewById(R.id.tv_username);//用户名
        tvLevel = (TextView) view.findViewById(R.id.tv_level);//等级
        llPersonalCenter = (LinearLayout) view.findViewById(R.id.ll_personal_center);//个人中心
        llMyJifen = (LinearLayout) view.findViewById(R.id.ll_my_jifen);//积分
        llLastInformation = (LinearLayout) view.findViewById(R.id.ll_last_information);//我的消息
        llSkin = (LinearLayout) view.findViewById(R.id.ll_skin);//主题换肤
        llNightMode = (LinearLayout) view.findViewById(R.id.ll_night_mode);//夜间模式
        llAdvise = (LinearLayout) view.findViewById(R.id.ll_advise);//意见反馈
        llAboutUs = (LinearLayout) view.findViewById(R.id.ll_about_us);//关于我们
//        textView = (TextView) view.findViewById(R.id.textView);
        llCheck = (LinearLayout) view.findViewById(R.id.ll_check);//检查更新
//        textCheck = (TextView) view.findViewById(R.id.textCheck);

        llHeaderBg.setOnClickListener(this);
        touxiang.setOnClickListener(this);
        tvUsername.setOnClickListener(this);
        tvLevel.setOnClickListener(this);
        llPersonalCenter.setOnClickListener(this);
        llMyJifen.setOnClickListener(this);
        llLastInformation.setOnClickListener(this);
        llSkin.setOnClickListener(this);
        llNightMode.setOnClickListener(this);
        llAdvise.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
//        textView.setOnClickListener(this);
        llCheck.setOnClickListener(this);
//        textCheck.setOnClickListener(this);


        return view;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_header_bg://头部线性布局
                Toast.makeText(mContext, "头部线性布局", Toast.LENGTH_SHORT).show();
                break;
            case R.id.touxiang://头像
                settouxiang();
//                Toast.makeText(mContext, "头像", Toast.LENGTH_SHORT).show();
                break;

            case   R.id.tv_username://用户名
                Util.showToast(mContext, "用户名暂不开放~");
//                Toast.makeText(mContext, "用户名", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_level://等级
                Util.showToast(mContext, "等级暂不开放~");
//                Toast.makeText(mContext, "等级", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_personal_center://个人中心
//                Toast.makeText(mContext, "个人中心", Toast.LENGTH_SHORT).show();
                intent = newIntent(getActivity(), Activity_my_info.class);
                startActivity(intent);
//                getActivity().onBackPressed();//销毁当前页面

                break;
            case R.id.ll_my_jifen://积分
//                Toast.makeText(mContext, "积分", Toast.LENGTH_SHORT).show();
                intent = newIntent(getActivity(), Activity_my_jifen.class);
                startActivity(intent);
                break;
            case R.id.ll_last_information://我的消息
                Util.showToast(mContext, "消息暂不开放~");
//                Toast.makeText(mContext, "我的消息", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_skin://主题换肤
                Util.showToast(mContext, "主题暂不开放~");
//                Toast.makeText(mContext, "主题换肤", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_night_mode://夜间模式
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                Uri data = Uri.parse("tel:" + "13536371189");
//                intent.setData(data);
//                startActivity(intent);
                Util.showToast(mContext, "夜间暂不开放~");
                //Toast.makeText(mContext, "夜间模式", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_advise://意见反馈
                intent = newIntent(getActivity(), Activity_Feedback.class);
                startActivity(intent);
                break;
            case R.id.ll_about_us://关于我们
                intent = newIntent(getActivity(), Activity_my_about.class);
                startActivity(intent);
//                Toast.makeText(mContext, "关于我们", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_check://检查更新
                intent = newIntent(getActivity(), About_update.class);
//                intent = newIntent(getActivity(), Zhifu.class);//支付
                startActivity(intent);
//                Toast.makeText(mContext, "检查更新", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
    //头像组件具体实现
    private void settouxiang() {
        new AvatarStudio.Builder(getActivity())
                .needCrop(true)
                .setTextColor(Color.BLUE)
                .dimEnabled(true)
                .setAspect(1, 1)
                .setOutput(200, 200)
                .setText("打开相机", "从相册中选取", "取消")
                .show(new AvatarStudio.CallBack() {
                    @Override
                    public void callback(final String uri) {
                        // Picasso.with(MainActivity.this).load(new File(uri)).into(mImageView);
                        setAvataor(uri);
                    }
                });
    }
    private void setAvataor(final String uri) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(uri));
                    new Handler(Looper.getMainLooper())
                            .post(new Runnable() {
                                @Override
                                public void run() {
                                    touxiang.setImageBitmap(bitmap);
                                }
                            });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public static Intent newIntent(Context context, Class c) {
        Intent intent = new Intent(context, c);
        return intent;
    }
//
//    public static Intent newIntent(Context context, Class c, String extra) {
//        Intent intent = new Intent(context, c);
//        intent.putExtra(EXTRA_STRING, extra);
//        return intent;
//    }





}

