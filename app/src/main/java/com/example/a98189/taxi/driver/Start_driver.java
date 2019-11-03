package com.example.a98189.taxi.driver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a98189.taxi.R;
import com.example.a98189.taxi.Utils.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 开始打车界面实现
 * 订单开始
 * view_driver
 *
 * 需要实现的功能
 * 前往几个上车点的路线及时间倒计时
 *
 */

public class Start_driver extends Activity implements View.OnClickListener {
    private Button kaiche;
    private  Intent intent;

    public ListView lv;
    public ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private Mybaseadapter list_item;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_driver);
        init();

    }

    private void init() {
        kaiche = findViewById(R.id.kaiche);//开始前往目的地按钮

        kaiche.setOnClickListener(this);

        list.clear();
        lv = findViewById(R.id.lv_contact);
        list_item = new Mybaseadapter();
        lv.setAdapter(list_item);
    }

    @Override
    public void onClick(View v) {
        //完成订单具体实现
        if (v == kaiche){
            intent = newIntent(Start_driver.this,Start_dingdan_fache.class);
            startActivity(intent);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //post请求
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username","chen")
                            .add("passwork","123456")
                            .build();
                    //服务端访问地址
                    Request request = new Request
                            .Builder()
                            .post(requestBody)
                            .url("查找乘客信息的url 接口6")
                            .build();
                    Response response = client.newCall(request).execute();
                    //得到服务器返回的数据后，调用parseJSONWithJSONObject进行解析
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
    private void parseJSONWithJSONObject(String jsonData) {
        if (jsonData != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonData);

                //获取数据中的code值，如果是1则正确
                String resultCode = jsonObject.getString("status");
                if (resultCode.equals("206")) {
                    //获取到json数据中里的data内容
                    JSONArray resultJsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < resultJsonArray.length(); i++) {
                        //循环遍历，获取json数据中data数组里的内容
                        JSONObject Object = resultJsonArray.getJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();
                        try {
                            String place = Object.getString("place");//上车点
                            String numbers = Object.getString("numbers");//电话号码
                            int name = Object.getInt("name");//乘客姓名

                            map.put("name", name);
                            map.put("numbers", numbers);
                            map.put("place", place);

                            //保存到ArrayList集合中
                            list.add(map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    handler.sendEmptyMessageDelayed(1, 100);

                }else{
                    Util.showToast(Start_driver.this, "当前无数据~");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //刷新
                    list_item.notifyDataSetChanged();
                    break;
            }
        }
    };

    public static Intent newIntent(Context context, Class c) {
        Intent intent = new Intent(context, c);
        return intent;
    }
    //listview适配器
    public class Mybaseadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = new ViewHolder();
            //判断convertView为空，使用LayoutInflater去加载布局。不为空则直接对convertView进行重用
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.view_driver,null);
                viewHolder.place = (TextView) convertView.findViewById(R.id.place);//上车点
                viewHolder.numbers = (TextView) convertView.findViewById(R.id.number);//电话号码
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);//乘客姓名
                viewHolder.call = convertView.findViewById(R.id.iphone);//打电话按钮

                convertView.setTag(viewHolder);

            } else {
                //重新获取ViewHolder
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.place.setText("上车点："+list.get(position).get("place").toString());
            viewHolder.numbers.setText("电话号码："+list.get(position).get("numbers").toString());
            viewHolder.name.setText("乘客："+list.get(position).get("name").toString());

            //liewview打电话按钮实现
            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel:" + finalViewHolder.numbers));
                    startActivity(intent);

                }
            });


            return convertView;
        }
    }

    final static class ViewHolder {
        TextView place;
        TextView numbers;
        TextView name;
        ImageButton call;

    }
}

