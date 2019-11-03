package com.example.a98189.taxi.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a98189.taxi.MainActivity;
import com.example.a98189.taxi.R;
import com.example.a98189.taxi.Utils.ClassSavePersonData;
import com.example.a98189.taxi.Utils.GlobleVariable;
import com.example.a98189.taxi.Utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 设置密码界面
 * 在这里获得手机和密码然后提交上服务器
 */

public class PassWordActivity extends AppCompatActivity {
    private Button button;
    private EditText editText;
    private EditText editText_ag;
    private String password;
    private String password_ag;
    private String phone;
    private static final String USER_PHONE = "PhoneLoginActivity.user_phone";

    private int numble = 1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.passwordlayout);


        button = (Button) findViewById(R.id.button_password);
        editText = (EditText) findViewById(R.id.et_password);
        editText_ag = (EditText) findViewById(R.id.et_password_ag);
        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString(USER_PHONE);
        //获得了电话后，加上密码，加入到数据库
        //加入数据库 ，在响应确定按钮之后执行

        //为editText设置监听器，是为了监听图片是否被点击,drawableRight 所加的方法是没有办法设置监听的，采用这种直接获得焦点的方法监听，
        // 后面-30是因为左右padding了15
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
                if (event.getX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - 50)) {
                    numble = numble + 1;

                    if ((numble & 1) != 0) {
                        //不可见，闭眼，默认是1,奇数
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        Drawable no_look = ContextCompat.getDrawable(getApplication(), R.mipmap.no_look);
                        Drawable lock = ContextCompat.getDrawable(getApplication(), R.mipmap.lock_air);
                        //没有下面的方法的话就点一下后全部消失
                        no_look.setBounds(0, 0, no_look.getMinimumWidth(), no_look.getMinimumHeight());
                        lock.setBounds(0, 0, lock.getMinimumWidth(), lock.getMinimumHeight());

                        editText.setCompoundDrawables(lock, null, no_look, null);
                    } else {
                        //
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        Drawable can_look = ContextCompat.getDrawable(getApplication(), R.mipmap.can_look);
                        Drawable lock = ContextCompat.getDrawable(getApplication(), R.mipmap.lock_air);
                        can_look.setBounds(0, 0, can_look.getMinimumWidth(), can_look.getMinimumHeight());
                        lock.setBounds(0, 0, lock.getMinimumWidth(), lock.getMinimumHeight());
                        editText.setCompoundDrawables(lock, null, can_look, null);
                    }
                    return true;

                }

                return false;
            }
        });


        editText_ag.setOnTouchListener(new View.OnTouchListener() {
            private static final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editText.getCompoundDrawables()[DRAWABLE_RIGHT] == null) {
                    return false;
                }
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (event.getX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - 50)) {
                    numble = numble + 1;

                    if ((numble & 1) != 0) {
                        //设置不可见，闭眼，默认是1，闭眼
                        editText_ag.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        Drawable no_look = ContextCompat.getDrawable(getApplication(), R.mipmap.no_look);
                        Drawable lock = ContextCompat.getDrawable(getApplication(), R.mipmap.lock_air);
                        no_look.setBounds(0, 0, no_look.getMinimumWidth(), no_look.getMinimumHeight());
                        lock.setBounds(0, 0, lock.getMinimumWidth(), lock.getMinimumHeight());
                        editText_ag.setCompoundDrawables(lock, null, no_look, null);
                    } else {
                        //
                        editText_ag.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        Drawable can_look = ContextCompat.getDrawable(getApplication(), R.mipmap.can_look);
                        Drawable lock = ContextCompat.getDrawable(getApplication(), R.mipmap.lock_air);
                        can_look.setBounds(0, 0, can_look.getMinimumWidth(), can_look.getMinimumHeight());
                        lock.setBounds(0, 0, lock.getMinimumWidth(), lock.getMinimumHeight());
                        editText_ag.setCompoundDrawables(lock, null, can_look, null);
                    }
                    return true;

                }

                return false;
            }
        });


        //监听检查输入是否为空，输入是否相等...
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = editText.getText().toString().trim();
                password_ag = editText_ag.getText().toString().trim();



                if (password.equals("") || password_ag.equals("")) {
                    Util.showToast(getApplicationContext(), "密码不能为空");
                } else if (!password.equals(password_ag)) {
                    Util.showToast(getApplicationContext(), "两次输入密码不一致");
                }else if (password.length() > 12 || password_ag.length() > 12) {
                    Util.showToast(getApplicationContext(), "密码过长啦~");
                } else {
                    OkHttpClient client = new OkHttpClient();

                    //POST请求
                    FormBody formBody = new FormBody.Builder()
                            .add("phone", phone)
                            .add("password",password)
                            .build();

                    final Request request = new Request.Builder()
                            .url("http://106.54.109.87/driver/public/index.php/index/register")
                            .post(formBody)
                            .build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String string = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(string);
                                String result = jsonObject.getString("status");

                                if (result.equals("201")){
                                    GlobleVariable.UID = phone;
                                    new ClassSavePersonData(GlobleVariable.UID, "Person", GlobleVariable.UID+".txt").run();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Util.showToast(getApplicationContext(),"注册成功");
                                            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Util.showToast(getApplicationContext(),"注册失败");
                                            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }

            }
        });


    }
}
