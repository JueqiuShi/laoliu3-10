package com.example.a98189.taxi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.a98189.taxi.base.BaseFragment;
import com.example.a98189.taxi.cehua_activity.Anquan;
import com.example.a98189.taxi.cehua_activity.Kefu;
import com.example.a98189.taxi.cehua_activity.Qiangbao;
import com.example.a98189.taxi.cehua_activity.Setting;
import com.example.a98189.taxi.cehua_activity.Xingcheng;
import com.example.a98189.taxi.fragment.DriverFragment;
import com.example.a98189.taxi.fragment.OrderFragment;
import com.example.a98189.taxi.fragment.StudentFragment;
import com.example.a98189.taxi.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 */


public class MainActivity extends FragmentActivity {
    private long exitTime = 0;//退出时间变量值
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return true;//拦截返回键
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if((System.currentTimeMillis() - exitTime )> 2000){
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }else {
            finish();
            System.exit(0);//销毁强制退出
        }
    }

    private RadioGroup mRg_main;
    private List<BaseFragment> mBaseFragment;

    /**
     * 侧滑框架
     */
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ImageView menu;


    /**
     * 选中的Fragment的对应的位置
     */
    private int position;

    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化View
        initView();
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
    }

    private void setListener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.rb_common_frame);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_common_frame://司机
                    position = 0;
                    break;
                case R.id.rb_thirdparty://乘客
                    position = 2;
                    break;

                case R.id.rb_other://我的
                    position = 3;
                    break;
                default:
                    position = 0;
                    break;
            }

            //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFrament(mContent, to);

        }
    }


    /**
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to   马上要切换到的Fragment，一会要显示
     */
    private void switchFrament(Fragment from, Fragment to) {
        if (from != to) {
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if (!to.isAdded()) {
                //to没有被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //添加to
                if (to != null) {
                    ft.add(R.id.fl_content, to).commit();
                }
            } else {
                //to已经被添加
                // from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //显示to
                if (to != null) {
                    ft.show(to).commit();
                }
            }
        }

    }

    /**
     * 根据位置得到对应的Fragment
     *
     * @return
     */
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new DriverFragment());//常用框架Fragment
        mBaseFragment.add(new OrderFragment());//第三方Fragment
        mBaseFragment.add(new StudentFragment());//自定义控件Fragment
        mBaseFragment.add(new MyFragment());//其他Fragment
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mRg_main = (RadioGroup) findViewById(R.id.rg_main);


        /**侧滑*/
//        drawerLayout = findViewById(R.id.activity_na);
        navigationView = findViewById(R.id.nav);
//        menu = findViewById(R.id.main_menu);
//        View headerView = navigationView.getHeaderView(0);//获取头布局
//        menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //点击菜单，跳出侧滑菜单
//                if (drawerLayout.isDrawerOpen(navigationView)) {
//                    drawerLayout.closeDrawer(navigationView);
//                } else {
//                    drawerLayout.openDrawer(navigationView);
//                }
//            }
//        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.xingcheng:
                        Intent intent1 = new Intent(MainActivity.this, Xingcheng.class);
                        startActivity(intent1);
                        break;
                    case R.id.anquan:
                        Intent intent2 = new Intent(MainActivity.this, Anquan.class);
                        startActivity(intent2);
                        break;
                    case R.id.qianbao:
                        Intent intent3 = new Intent(MainActivity.this, Qiangbao.class);
                        startActivity(intent3);
                        break;
                    case R.id.shezhi:
                        Intent intent4 = new Intent(MainActivity.this, Setting.class);
                        startActivity(intent4);
                        break;
                    case R.id.kefu:
                        Intent intent5 = new Intent(MainActivity.this, Kefu.class);
                        startActivity(intent5);
                        break;

                }
//                Toast.makeText(MainActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
//                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }
}
