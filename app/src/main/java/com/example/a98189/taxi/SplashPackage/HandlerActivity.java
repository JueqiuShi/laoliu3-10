package com.example.a98189.taxi.SplashPackage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.a98189.taxi.FirstActivity;
import com.example.a98189.taxi.Login.LoginActivity;
import com.example.a98189.taxi.MainActivity;
import com.example.a98189.taxi.R;
import com.example.a98189.taxi.Utils.GlobleVariable;
import com.example.a98189.taxi.Utils.Util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Timer;
import java.util.TimerTask;


/**废弃不用*/

public class HandlerActivity extends Activity {
    private Intent intent;
    Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //不显示状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        //6.0动态权限(文件)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
//            提示用户的 dialog(6.0动态权限)
            showMissingPermissionDialog();//TODO：无法封装，原因暂时未知，很气
        }else{
            //延时0.7秒，在这段时间内读取本地数据，判断是否需要登录
            timer.schedule(task, 700);
        }
        handler.sendEmptyMessageDelayed(0, 3000);


    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            //执行一次后销毁本页面
            finish();
        }

    };

    /**
     * 检测是否已经有登录，通过检查文件夹内是否有存储uid的txt文件
     * 在这里直接读取名字为uid的txt文件
     * */
    private void CheckForUID() {
        try {
            File file = new File(GlobleVariable.SD_CARD_PATH + "/" + GlobleVariable.DATA_SAVE_DIR + "/Person");
            if(file.exists()){
                File[] allFiles = file.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        // TODO Auto-generated method stub
                        if (filename.endsWith(".txt")) {
                            GlobleVariable.UID = filename.substring(0, filename.length()-4);//
                        }else {
                            return false;
                        }
                        return false;
                    }
                });

            }
        } catch (Exception e) {
            Log.e("", "readTxt: ---------------" + e.toString());

        }

        //没有登录记录
        if (GlobleVariable.UID == null) {
            Intent intent = newIntent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            HandlerActivity.this.finish();
        } else {
            //已有登录记录
            Looper.prepare();
            Util.showToast(getApplicationContext(),"欢迎回来");
            Intent intent = newIntent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            HandlerActivity.this.finish();
            Looper.loop();
        }

    }

    public static Intent newIntent(Context context, Class c) {
        Intent intent = new Intent(context, c);
        return intent;
    }

    //延时后，检测本地文件，判断是否自动登录
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            CheckForUID();
        }
    };
    /**
     * 提示用户的 dialog(6.0动态权限)
     */
    protected void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("该应用缺少权限，可能会导致应用无法正常使用。\n\n请点击\"设置\"→\"权限管理\"打开所需权限，并重启应用。");
        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("info", "8--权限被拒绝,此时不会再回调onRequestPermissionsResult方法");
                    }
                });
        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("info", "4,需要用户手动设置，开启当前app设置界面");
                        startAppSettings();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }
    /**
     * 打开     App设置界面(6.0动态权限)
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
