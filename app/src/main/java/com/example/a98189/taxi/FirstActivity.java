package com.example.a98189.taxi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.a98189.taxi.Login.LoginActivity;

import com.example.a98189.taxi.Utils.GlobleVariable;
import com.example.a98189.taxi.Utils.Util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动类。
 */
public class FirstActivity extends Activity {
    private TextView textView;
    Timer timer = new Timer();
    private static Boolean isLogin =false;//判断用户是否登录过
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //6.0动态权限(文件)
        /**
         * 以后改善
         * 判断多个权限时候已打开
         */
        //判断读取权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
//            提示用户的 dialog(6.0动态权限)
            showMissingPermissionDialog();//TODO：无法封装，原因暂时未知，很气
        }else{
            //延时0.7秒，在这段时间内读取本地数据，判断是否需要登录
//            timer.schedule(task, 700);
//            timer.schedule(task, 2000);
            SendMessage();//发送消息


        }

    }

    /**发送消息*/
    private void SendMessage() {
        /**测试*/
        SharedPreferences preferences = getSharedPreferences("isLogin", MODE_PRIVATE );
        isLogin = preferences.getBoolean("isLogin", false);
        /**
         *判断用户是否登录过
         */
        if (isLogin) {
            handler.sendEmptyMessageDelayed(2, 1000);//登录过
            Util.showToast(getApplicationContext(),"欢迎回来");
        } else {
            handler.sendEmptyMessageDelayed(1, 1000);//没登录过
        }
//        finish();
//        //实例化Editor对象
//        SharedPreferences.Editor editor = preferences.edit();
//        //存入数据
//        editor.putBoolean("isLogin", true);
//        //提交修改
//        editor.commit();
    }

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
            FirstActivity.this.finish();
        } else {
            //已有登录记录
            Looper.prepare();
//            Util.showToast(getApplicationContext(),"欢迎回来");
            Toast.makeText(this, "欢迎回来", Toast.LENGTH_SHORT);

            /***测试*/
            /*
            //删除本地保存的文件
            ClassSavePersonData classSavePersonData   =  new ClassSavePersonData(GlobleVariable.UID, "Person", GlobleVariable.UID + ".txt");
            classSavePersonData.cleanOldFile(GlobleVariable.UID + ".txt");
            Intent intent = newIntent(getApplicationContext(), LoginActivity.class);

             */


            Intent intent = newIntent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            FirstActivity.this.finish();
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
//            CheckForUID();

            /**测试*/
            SharedPreferences preferences = getSharedPreferences("isLogin", MODE_PRIVATE );
            isLogin = preferences.getBoolean("isLogin", true);
            /**
             *判断用户是否登录过
             */
            if (isLogin) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                Util.showToast(getApplicationContext(),"欢迎回来");
                Util.show(getApplicationContext(),"欢迎回来");
                finish();
            }
            finish();
            //实例化Editor对象
            SharedPreferences.Editor editor = preferences.edit();
            //存入数据
            editor.putBoolean("isLogin", false);
            //提交修改
            editor.apply();
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



    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    //执行一次后销毁本页面
                    finish();
                    break;
                case 2:
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    //执行一次后销毁本页面
                    finish();
                    break;
            }


        }

    };
}
