package com.example.a98189.taxi.SplashPackage;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.a98189.taxi.FirstActivity;
import com.example.a98189.taxi.Login.LoginActivity;
import com.example.a98189.taxi.MainActivity;
import com.example.a98189.taxi.R;
import com.example.a98189.taxi.Utils.Util;

import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面
 */
public class GuideActivtiy extends Activity {
    private ViewPager mViewPager;
    /**
     * 引导界面三张图片
     */
    private int[] mImageIds = new int[]{R.drawable.group_1, R.drawable.group_2, R.drawable.group_3};
    private ArrayList<ImageView> mImageViewList;
    private LinearLayout llContainer;
    private ImageView ivRedPoint;
    private int mPaintDis;
    private Button start_btn;
    //检测权限的返回值
//    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;

    String[] permission = {Manifest.permission.CAMERA,//相机
            //存储空间
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
            //短信
            Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_WAP_PUSH,Manifest.permission.RECEIVE_MMS
            ,Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS,//Manifest.permission.READ_CELL_BROADCASTS,错误
            //电话
            Manifest.permission.CALL_PHONE,
            //定位(位置信息)
            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
    };//需要的权限
    private final int mRequestCode = 123;//权限请求码
    String title = "获取权限";
    String content = "为了老刘拼车能够更好的使用\n我们需要获取一下相应的权限";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //不显示状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guide_activtiy);
//        设置权限
//        setQuanxiang();
        initPermission(permission,title,content);
        initView();
    }
    /**
     * 初始化用户权限
     * @param permissions 需要的权限
     * @param title 我们自己对话框的标题
     * @param content 我们自己对话框的内容
     */
    private void initPermission(String[] permissions, String title, String content) {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionList = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {//for循环把需要授权的权限都添加进来
                if (ContextCompat.checkSelfPermission(GuideActivtiy.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {  //未授权就进行授权
                    permissionList.add(permissions[i]);
                }
            }
            //如果permissionList是空的，说明没有权限需要授权,什么都不做，该干嘛干嘛，否则就发起授权请求
            if (!permissionList.isEmpty()) {
                showDialogTipUserRequestPermission(permissionList, title, content);
            }
        }
    }

    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission(final List<String> permissionList, String title, String content) {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission(permissionList);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }
    // 开始提交请求权限
    private void startRequestPermission(List<String> permissionList) {
        if (!permissionList.isEmpty()) {//不为空就进行授权申请
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        }
    }
    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults.length > 0) {//安全写法，如果小于0，肯定会出错了
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        switch (grantResult){
                            case PackageManager.PERMISSION_GRANTED://同意授权0
                                break;
                            case PackageManager.PERMISSION_DENIED://拒绝授权-1
                                Util.showToast(GuideActivtiy.this, permissions[i]+"权限获取失败");
//                                finish();
                                Log.e("------------",permissions[i]+"权限获取失败");
                                break;
                        }
                    }
                }
            }
        }
    }


//    private void setQuanxiang() {//若系统低于Android M
//        if (Build.VERSION.SDK_INT < 23) {
//            return;
//
//        }
//        //系统版本大于Android M 还未授予权限
//        int checkCallPhonePermission =
//                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
//        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_CALL_PHONE);
//            return;
//        }
//        //系统版本大于Android M 且 已授予权限
//
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_ASK_CALL_PHONE:
//                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    //权限被拒
//                    Toast.makeText(GuideActivtiy.this, "权限被拒", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                break;
//        }
//    }



    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        ivRedPoint = (ImageView) findViewById(R.id.iv_red);
        start_btn = (Button) findViewById(R.id.start_btn);

        start_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击进入的时候直接跳转到登录界面
                Intent intent = new Intent(GuideActivtiy.this, FirstActivity.class);
                startActivity(intent);
                finish();
            }
        });

        initData();
        GuideAdapter adapter = new GuideAdapter();
        //添加动画效果
//        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setAdapter(adapter);

        //监听布局是否已经完成  布局的位置是否已经确定
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //避免重复回调        出于兼容性考虑，使用了过时的方法
                ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //布局完成了就获取第一个小灰点和第二个之间left的距离
                mPaintDis = llContainer.getChildAt(1).getLeft() - llContainer.getChildAt(0).getLeft();
                System.out.println("距离：" + mPaintDis);
            }
        });


        //ViewPager滑动Pager监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动过程中的回调
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当滑到第二个Pager的时候，positionOffset百分比会变成0，position会变成1，所以后面要加上position*mPaintDis
                int letfMargin = (int) (mPaintDis * positionOffset) + position * mPaintDis;
                //在父布局控件中设置他的leftMargin边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
                params.leftMargin = letfMargin;
                ivRedPoint.setLayoutParams(params);
            }


            /**
             * 设置按钮最后一页显示，其他页面隐藏
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                System.out.println("position:" + position);
                if (position == mImageViewList.size() - 1) {
                    start_btn.setVisibility(View.VISIBLE);
                } else {
                    start_btn.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println("state:" + state);
            }
        });
    }

    class GuideAdapter extends PagerAdapter {

        //item的个数
        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //初始化item布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImageViewList.get(position);
            container.addView(view);
            return view;
        }

        //销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    protected void initData() {
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            //创建ImageView把mImgaeViewIds放进去
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            //添加到ImageView的集合中
            mImageViewList.add(view);
            //小圆点
            ImageView pointView = new ImageView(this);
            pointView.setImageResource(R.drawable.shape_point1);
            //初始化布局参数，父控件是谁，就初始化谁的布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                //当添加的小圆点的个数超过一个的时候就设置当前小圆点的左边距为20dp;
                params.leftMargin = 20;
            }
            //设置小灰点的宽高包裹内容
            pointView.setLayoutParams(params);
            //将小灰点添加到LinearLayout中
            llContainer.addView(pointView);
        }
    }


}