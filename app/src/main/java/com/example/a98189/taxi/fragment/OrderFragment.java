package com.example.a98189.taxi.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a98189.taxi.base.BaseFragment;
import com.example.a98189.taxi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by  liangjiachneg.Date: 2019/9/28.Time: 9:51
 * <p>
 * 这个类的作用是：订单页面
 */
public class OrderFragment extends BaseFragment {
    private static final String TAG = OrderFragment.class.getSimpleName();//"DriverFragment"

    //订单
    public ListView listview;
    public ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private Context context;

    @Override
    protected View initView() {
        Log.e(TAG,"第三方Fragment页面被初始化了...");
        View view = View.inflate(mContext, R.layout.fragment_order,null);

      //Intent intent=getIntent();

        //用getXxxExtra()取出对应类型的数据。
//        int year = intent.getIntExtra("num1",0);
//        int month = intent.getIntExtra("num2",0);
//        int day = intent.getIntExtra("num3",0);
//        int hour = intent.getIntExtra("num4",0);
//        int minute = intent.getIntExtra("num5",0);
//        String peoplenumber=intent.getStringExtra("num6");
//        String place=intent.getStringExtra("num7");
//        Log.d("SetLocation", "year is " + year);
        list.clear();
        listview = view.findViewById(R.id.listview);
        init();


        return view;
    }
    @Override
    protected void initData() {
        super.initData();
//        Log.e(TAG, "第三方Fragment数据被初始化了...");
//        textView.setText("第三方页面");
    }


    /**订单*/
    private Mybaseadapter list_item;

    private void init() {
//        list.clear();

//        listview = findViewById(R.id.lv_contact);
        list_item = new Mybaseadapter();
        Log.e(TAG, "第三方Fragment数据被初始化了..."+list_item+"");
        listview.setAdapter(list_item);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //服务端访问地址
                    Request request = new Request
                            .Builder()
                            .url("http://112.124.8.83:8001/query").build();
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
                String resultCode = jsonObject.getString("code");
                if (resultCode.equals("1")) {
                    //获取到json数据中里的data内容
                    JSONArray resultJsonArray = jsonObject.getJSONArray("data");
                    Log.d("MainActivity", "data+++" + resultJsonArray);
                    for (int i = 0; i < resultJsonArray.length(); i++) {
                        //循环遍历，获取json数据中data数组里的内容
                        JSONObject Object = resultJsonArray.getJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();
                        try {
                            String contact = Object.getString("contact");
                            String boxNo = Object.getString("boxNo");
                            String inputTime = Object.getString("inputTime");


                            map.put("contact", contact);
                            map.put("boxNo", boxNo);
                            map.put("inputTime", inputTime);

                            //保存到ArrayList集合中
                            list.add(map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    handler.sendEmptyMessageDelayed(1, 100);

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
                convertView = getLayoutInflater().inflate(R.layout.view_main,null);
                viewHolder.contact = (TextView) convertView.findViewById(R.id.tvCargono);
                viewHolder.boxNo = (TextView) convertView.findViewById(R.id.tvVariety);
                viewHolder.inputTime = (TextView) convertView.findViewById(R.id.tvMarkno);

                convertView.setTag(viewHolder);

            } else {
                //重新获取ViewHolder
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.contact.setText("路线："+list.get(position).get("contact").toString());
            viewHolder.boxNo.setText("剩余空位："+list.get(position).get("boxNo").toString());
            viewHolder.inputTime.setText("车辆颜色："+list.get(position).get("inputTime").toString());

            return convertView;
        }
    }
    final static class ViewHolder {
        TextView contact;
        TextView boxNo;
        TextView inputTime;
    }
}
