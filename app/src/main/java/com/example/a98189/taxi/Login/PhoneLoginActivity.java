package com.example.a98189.taxi.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.a98189.taxi.MainActivity;

import com.example.a98189.taxi.Utils.ClassSavePersonData;
import com.example.a98189.taxi.Utils.GlobleVariable;
import com.example.a98189.taxi.Utils.Util;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 第三方手机注册界面
 */
public class PhoneLoginActivity extends AppCompatActivity {
    private static final String USER_PHONE = "PhoneLoginActivity.user_phone";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //根据SDK要求加入的代码，初始化SDK
        MobSDK.init(this, "2c6fa43197a68", "a62999e6e5691d1c89c060acd5672fa5");
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    final String phone = (String) phoneMap.get("phone");
                    //应用了第三方验证码登录 ，没有办法在获得手机号码之前进行是数据库的查询。
                    //调用数据库来查询是否有对应的数据 判断是否进入主页
                    OkHttpClient client = new OkHttpClient();

                    //POST请求
                    FormBody formBody = new FormBody.Builder()
                            .add("phone",phone)
                            .build();

                    final Request request = new Request.Builder()
                            .url("http://62.234.128.12/driver/public/index.php/index/login")
                            .post(formBody)
                            .build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Util.showToast(getApplicationContext(), "好像出问题了~");
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String jsonString = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(jsonString);
                                int result = Integer.parseInt(jsonObject.getString("status"));
                                if (result == 401) {
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(),"手机已注册，直接登录", Toast.LENGTH_SHORT);
                                    Intent i = newIntent(PhoneLoginActivity.this, MainActivity.class, phone);
                                    GlobleVariable.UID = phone;
                                    new ClassSavePersonData(GlobleVariable.UID, "Person", GlobleVariable.UID+".txt").run();
                                    startActivity(i);
                                    finish();
                                    Looper.loop();
                                } else if(result == 410){
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(),"注册失败", Toast.LENGTH_SHORT);
                                    Looper.loop();
                                }else if(result == 400){
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(),"请求错误", Toast.LENGTH_SHORT);
                                    Looper.loop();
                                }else if(result == 402){
                                    Intent i = newIntent(PhoneLoginActivity.this, PassWordActivity.class, phone);
                                    startActivity(i);
                                    finish();
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
            }
        });

        registerPage.show(this);
        finish();


    }

    //重写newIntent方法，方便直接传入phone
    public static Intent newIntent(Context context, Class c, String phone) {
        Intent i = new Intent(context, c);
        i.putExtra(USER_PHONE, phone);
        return i;
    }


}
