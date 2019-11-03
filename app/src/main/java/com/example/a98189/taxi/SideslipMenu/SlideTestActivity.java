package com.example.a98189.taxi.SideslipMenu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a98189.taxi.MainActivity;
import com.example.a98189.taxi.R;


/**
 * 侧滑菜单：https://blog.csdn.net/u013184970/article/details/82882107
 */
public class SlideTestActivity extends Activity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ImageView menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_test);
        initView();
    }

    private void initView(){
        drawerLayout = findViewById(R.id.activity_na);
        navigationView = findViewById(R.id.nav);
        menu = findViewById(R.id.main_menu);
        View headerView = navigationView.getHeaderView(0);//获取头布局
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击菜单，跳出侧滑菜单
                if (drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.closeDrawer(navigationView);
                }else{
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(SlideTestActivity.this,item.getTitle().toString(),Toast.LENGTH_SHORT).show();
//                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }

}
