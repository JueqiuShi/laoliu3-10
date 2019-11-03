package com.example.a98189.taxi.Login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a98189.taxi.MainActivity;
import com.example.a98189.taxi.R;
import com.example.a98189.taxi.Utils.ClassSavePersonData;
import com.example.a98189.taxi.Utils.GlobleVariable;
import com.example.a98189.taxi.Utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by  liangjiachneg.Date: 2019/9/29.Time: 20:10
 * <p>
 * 这个类的作用是：登录界面
 */
public class LoginActivity extends AppCompatActivity {
    private EditText editText;
    private EditText editText_phone;
    private String user_phone;
    private String user_password;
    private Button button;
    private ImageView reg_qq;
    private ImageView reg_phone;
    private ImageView reg_weibo;
    private TextView user_pact;
    public static LoginActivity instance;
    private static final String TAG = "LoginActivity";

    private static final String EXTRA_STRING = "LoginActivity.extra_string";
    private static final String PHONE_STRING = "phone_string";
    private String id;

    private int numble = 1;

    /**
     * 返回键禁用
     * 按返回键回到MyFragment,问题没解决
     * 以后在返回键销毁所有activity并退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        reg_phone = (ImageView) findViewById(R.id.register_number);
        user_pact = (TextView) findViewById(R.id.tv_user_pact);
        editText_phone = (EditText) findViewById(R.id.edit_phone);
        button = (Button) findViewById(R.id.button_login);
        instance = this;

        //获取用户输入的密码
        editText = (EditText) findViewById(R.id.edit_password);

        //监听EditText，变换右侧眼睛图标
        editText.setOnTouchListener(new View.OnTouchListener() {
            private static final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editText.getCompoundDrawables()[DRAWABLE_RIGHT] == null) {
                    return false;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (event.getX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - 150)) {
                    numble = numble + 1;

                    if ((numble & 1) != 0) {
                        //不可见，闭眼，默认是1,奇数
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        Drawable no_look = ContextCompat.getDrawable(getApplication(), R.mipmap.no_look);

                        //没有下面的方法的话就点一下后全部消失
                        no_look.setBounds(0, 0, no_look.getMinimumWidth(), no_look.getMinimumHeight());


                        editText.setCompoundDrawables(null, null, no_look, null);
                    } else {
                        //
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        Drawable can_look = ContextCompat.getDrawable(getApplication(), R.mipmap.can_look);

                        can_look.setBounds(0, 0, can_look.getMinimumWidth(), can_look.getMinimumHeight());

                        editText.setCompoundDrawables(null, null, can_look, null);
                    }
                    return true;

                }

                return false;
            }
        });

        //登录按钮点击响应
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_password = editText.getText().toString().trim();
                user_phone = editText_phone.getText().toString().trim();
                if (user_password.equals("") || user_phone.equals("")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Util.showToast(getApplicationContext(), "账号密码不能为空");
                        }
                    });
                    return;
                }

                OkHttpClient client = new OkHttpClient();

                //POST请求
                FormBody formBody = new FormBody.Builder()
//                        .add("phone", user_phone)
                        .add("password",user_password)
                        .add("phone", user_phone)
                        .build();

                /**
                 * 使用OkHttp3做网络请求框架时，如果是http请求而非https请求，会导致请求失败
                 * 因为Android P之后系统限制了明文的网络请求，非加密请求会被系统禁止掉。
                 * */
                Request request = new Request.Builder()
                        .url("http://62.234.128.12/driver/public/index.php/index/login")
                        .post(formBody)				//默认就是GET请求就可以不写，这里是post
                        .build();
                Log.e("EEEEEEEEEEEEEE",request.toString());

                okhttp3.Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, final IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("EEEEEEEEEEEEEE",e.toString());
                                Util.showToast(getApplicationContext(), "后台君好像生病了"+String.valueOf(e));
//                                Log.e("------------",e.toString());
                            }
                        });
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                        String jsonString = response.body().string();

                        // Log.i(TAG,"response"+response.body().string());
                        try {
                            JSONObject jsonObject = new JSONObject(jsonString);

                            int result = Integer.parseInt(jsonObject.getString("status"));

                            if (result == 405) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Util.showToast(getApplicationContext(), "密码错误或该用户不存在");
                                    }
                                });
                            }
                            if (result == 202) {
                                //将ID传入下一个Activity并且启动
                                Intent intent = newIntent(getApplicationContext(), MainActivity.class, user_phone);
                                intent.putExtra(PHONE_STRING, user_phone);
                                GlobleVariable.UID = user_phone;
//                                new ClassSavePersonData(GlobleVariable.UID, "Person", GlobleVariable.UID + ".txt").run();
                                saveMessage();//保存用户登录过
                                startActivity(intent);
                                LoginActivity.this.finish();
                            }
                            if (result == 400) {
                                Looper.prepare();
                                Util.showToast(getApplicationContext(), "好像出错了~");
                                Looper.loop();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Looper.prepare();
                            Util.showToast(getApplicationContext(), "好像出错了~");
                            Looper.loop();
                        }
                    }
                });

            }
        });
//


        /**测试*/
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OkHttpClient okHttpClient = new OkHttpClient();
//                RequestBody requestBody = new FormBody.Builder()
//                        .add("password", editText.getText().toString().trim())
//                        .add("phone", editText_phone.getText().toString().trim())
//                        .build();
//                Request request = new Request.Builder()
//                        .url("http://106.54.109.87/driver/public/index.php/index/login")
//                        .post(requestBody)
//                        .build();
//
//                okHttpClient.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(okhttp3.Call call, IOException e) {
//                        Util.showToast(getApplicationContext(), "后台君好像生病了"+e.toString());
//                        Log.e("EEEEEEEEEEEEEE",e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
//                        String jsonString = response.body().string();
//                        // Log.i(TAG,"response"+response.body().string());
//                        try {
//                            JSONObject jsonObject = new JSONObject(jsonString);
//
//                            int result = Integer.parseInt(jsonObject.getString("status"));
//
//                            if (result == 405) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Util.showToast(getApplicationContext(), "密码错误或该用户不存在");
//                                    }
//                                });
//                            }
//                            if (result == 202) {
//                                //将ID传入下一个Activity并且启动
//                                Intent intent = newIntent(getApplicationContext(), MainActivity.class, user_phone);
//                                intent.putExtra(PHONE_STRING, user_phone);
//                                GlobleVariable.UID = user_phone;
//                                new ClassSavePersonData(GlobleVariable.UID, "Person", GlobleVariable.UID + ".txt").run();
//                                startActivity(intent);
//                                LoginActivity.this.finish();
//                            }
//                            if (result == 400) {
//                                Looper.prepare();
//                                Util.showToast(getApplicationContext(), "好像出错了~");
//                                Looper.loop();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Looper.prepare();
//                            Util.showToast(getApplicationContext(), "好像出错了~");
//                            Looper.loop();
//                        }
//                    }
//
//                });
//            }
//        });






        reg_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = newIntent(getApplicationContext(), PhoneLoginActivity.class);
                startActivity(intent);

            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //测试，暂定uid为123
//                new ClassSavePersonData( "123", "Person", "123.txt").run();
//                Intent intent = newIntent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                LoginActivity.this.finish();
//            }
//
//        });
        //用户协议点击


    }

    /**保存用户登录过*/
    private void saveMessage() {
        SharedPreferences preferences = getSharedPreferences("isLogin", MODE_PRIVATE );
        //实例化Editor对象
        SharedPreferences.Editor editor = preferences.edit();
        //存入数据
        editor.putBoolean("isLogin", true);
        //提交修改
        editor.commit();
    }


    public static Intent newIntent(Context context, Class c) {
        Intent intent = new Intent(context, c);
        return intent;
    }

    public static Intent newIntent(Context context, Class c, String extra) {
        Intent intent = new Intent(context, c);
        intent.putExtra(EXTRA_STRING, extra);
        return intent;
    }

}
