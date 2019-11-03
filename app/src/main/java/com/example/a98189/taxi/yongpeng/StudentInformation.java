package com.example.a98189.taxi.yongpeng;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a98189.taxi.MainActivity;
import com.example.a98189.taxi.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 永鹏
 */
public class StudentInformation extends AppCompatActivity {

    private EditText name;
    private EditText sex;
    private EditText classno;
    private EditText schoolno;
    private EditText tel;
    private EditText urgenttel;
    private ImageView imageView;
    private Button button;
    private Button button1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2019-09-30 09:54:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        name = (EditText) findViewById(R.id.name);//姓名
        sex = (EditText) findViewById(R.id.sex);//性别
        classno = (EditText) findViewById(R.id.classno);//班级
        schoolno = (EditText) findViewById(R.id.schoolno);//学号
        tel = (EditText) findViewById(R.id.tel);//手机号码
        urgenttel = (EditText) findViewById(R.id.urgenttel);//紧急联系人手机号码
        imageView = (ImageView) findViewById(R.id.imageView);//上传图片
        button = (Button) findViewById(R.id.button);//返回按钮
        button1 = (Button) findViewById(R.id.button1);//提交按钮

    }

    public void onClick(View v) {

        //按钮的点击事件
        switch (v.getId()) {
            case R.id.button:
                button.setOnClickListener(new View.OnClickListener() { //为关闭按钮创建监听器
                    @Override
                    public void onClick(View v) {
                        finish();//关闭当前activity
                    }
                });

            case R.id.button1:

                //获取输入框内容
                String namelong = name.getText().toString();
                String sexlong = sex.getText().toString();
                String classnolong = classno.getText().toString();
                String schoolnolong = schoolno.getText().toString();
                String tellong = tel.getText().toString();
                String urgenttellong = urgenttel.getText().toString();
                //获取网络上的servlet路径
                String path = "                  ";//后台路径
                //        new postTask().execute(namelong, sexlong, classnolong, schoolnolong, tellong, urgenttellong, path);

                //输入框内容为空时，弹出提示框
                if (TextUtils.isEmpty(namelong)) {
                    Toast.makeText(StudentInformation.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(sexlong)) {
                    Toast.makeText(StudentInformation.this, "性别不能为空", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(classnolong)) {
                    Toast.makeText(StudentInformation.this, "班级不能为空", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(schoolnolong)) {
                    Toast.makeText(StudentInformation.this, "学号不能为空", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(tellong)) {
                    Toast.makeText(StudentInformation.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(urgenttellong)) {
                    Toast.makeText(StudentInformation.this, "紧急联系人号码不能为空", Toast.LENGTH_SHORT).show();
                }


                class postTask extends AsyncTask {

                    @Override
                    protected Object doInBackground(Object[] params) {
                        //依次获取用户名，密码与路径
                        String namelong = params[0].toString();
                        String sexlong = params[1].toString();
                        String classnolong = params[2].toString();
                        String schoolnolong = params[3].toString();
                        String tellong = params[4].toString();
                        String urgenttellong = params[5].toString();
                        String path = params[6].toString();
                        try {
                            //获取网络上get方式提交的整个路径
                            URL url = new URL(path);
                            //打开网络连接
                            HttpURLConnection conn = null;
                            try {
                                conn = (HttpURLConnection) url.openConnection();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //设置提交方式
                            conn.setRequestMethod("POST");
                            //设置网络超时时间
                            conn.setConnectTimeout(5000);
                            //界面上所有的参数名加上他的值
                            String s = "name=" + namelong + "&sex" + sexlong + "&classno" + classnolong + "&schoolno" + schoolnolong + "&tel" + tellong + "&urgenttel" + urgenttellong;
                            //获取请求头
                            conn.setRequestProperty("Content-Length", s.length() + "");//键是固定的
                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//键和值是固定的
                            //设置允许对外输出数据
                            conn.setDoOutput(true);
                            //把界面上的所有数据写出去
                            OutputStream os = conn.getOutputStream();
                            os.write(s.getBytes());
                            if (conn.getResponseCode() == 200) {
                                //用io流与web后台进行数据交互
                                InputStream is = conn.getInputStream();
                                //字节流转字符流
                                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                                //读出每一行的数据
                                String str = br.readLine();
                                //返回读出的每一行的数据
                                return str;
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }


                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        //获取Android studio与web后台数据交互获得的值
                        String s = (String) o;
                        //Android studio与web后台数据交互获得的值
                        Toast.makeText(StudentInformation.this, s, Toast.LENGTH_SHORT).show();
                    }
                }

//获取相册
      /*      case R.id.imageView:
                private void action() {
                new AvatarStudio.Builder(MainActivity.this)
                        .needCrop(true)
                        .setTextColor(Color.BLUE)
                        .dimEnabled(true)
                        .setAspect(1, 1)
                        .setOutput(200, 200)
                        .setText("打开相机", "从相册中选取", "取消")
                        .show(new AvatarStudio.CallBack() {

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
                                            ImageView.setImageBitmap(bitmap);
                                        }
                                    });
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();*/
        }
    }
}
