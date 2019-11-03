package com.example.a98189.taxi.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a98189.taxi.HttpUtil;
import com.example.a98189.taxi.R;
import com.example.a98189.taxi.Refresh_Dialog;
import com.example.a98189.taxi.driver.False_own;
import com.example.a98189.taxi.driver.Success_own;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 订单
 */
public class SetLocation extends AppCompatActivity implements View.OnClickListener {
    public ListView lv;
    public ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private Context context;
    //转圈圈等待动画
//    private Thread waitdialog;
//    Refresh_Dialog refresh_dialog;
    String  url,url_choose;
    String peoplenumber,place,location;
    private ProgressBar pb_loading;
    private ImageView error_image;
    private TextView error_message;
    private int Time=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setlocation);

        lv = findViewById(R.id.lv_contact);

        Intent intent=getIntent();
        //用getXxxExtra()取出对应类型的数据。
        int year = intent.getIntExtra("trueyear",0);
        int month = intent.getIntExtra("truemonth",0);
        int day = intent.getIntExtra("trueday",0);
        int hour = intent.getIntExtra("truehour",0);
        int minute = intent.getIntExtra("trueminute",0);
        int second = intent.getIntExtra("truesecond",0);
        peoplenumber=intent.getStringExtra("truepeoplenumber");
        place=intent.getStringExtra("trueplace");
        location = intent.getStringExtra("location");
        pb_loading = findViewById(R.id.pb_loading);
        error_image = findViewById(R.id.error_image);
        error_message = findViewById(R.id.error_message);

        //起始时间，结束时间
        String time_end;String time_start;
        if (++hour<24&--hour>0){
            hour++;
        }else if(++hour>=24) {
            hour = 24;
        }else if(--hour<=0){
            hour = 1;
        }
        time_end = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
        time_start = year+"-"+month+"-"+day+" "+(hour-2)+":"+minute+":"+second;
        long times_end = 0;long times_start = 0;
        /**
         * 字符串时间类型转时间戳
         */
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //string转date
            Date date_end = formatter.parse(time_end);
            Date date_stard = formatter.parse(time_start);
            //date转时间戳
            times_end = date_end.getTime()/1000;
            times_start = date_stard.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }



        url = "http://62.234.128.12/driver/public/index.php/passenger/seek/time_end/"+times_end+
                "/time_start/"+times_start+"/numbers/"+peoplenumber;
        /**
         * 下拉刷新
         */
        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        start();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                init();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

    }

    private Mybaseadapter list_item;
    private void start(){
        //实例化适配器
        list_item = new Mybaseadapter();
        sendRequestWithOKHttp();

    }


    private void init() {
        //清除
        list.clear();
/*
        if (list!=null&&list.size()>0) {
            lv.setAdapter(list_item);//设置适配器
            pb_loading.setVisibility(View.GONE);
        }else {
            pb_loading.setVisibility(View.VISIBLE);
        }

 */
        sendRequestWithOKHttp();
        //post请求



//         Handler handler1 = new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 1:
//                        //刷新
//                        list_item.notifyDataSetChanged();
//                        break;
//                }
//            }
//        };
//        waitdialog = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    refresh_dialog =new Refresh_Dialog();
//                    Log.e("--------","waitdialog");
//
//                }
//            }
//        });
//        waitdialog.start();
    }




    @SuppressLint("HandlerLeak")
    Handler handler1 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (Time <= 3) {
                        Time++;
                        pb_loading.setVisibility(View.VISIBLE);
                        handler1.sendEmptyMessageDelayed(2, 1000);
                    } else {
                        pb_loading.setVisibility(View.GONE);
                        error_image.setVisibility(View.VISIBLE);
                        error_message.setVisibility(View.VISIBLE);
                    }
                case 2:
//                    //刷新
                    list_item.notifyDataSetChanged();
                    if (list_item!=null&&!list_item.isEmpty()) {
                        lv.setAdapter(list_item);//设置适配器
                        pb_loading.setVisibility(View.GONE);
                    }else {
                        pb_loading.setVisibility(View.VISIBLE);

                    }
                    list_item.notifyDataSetChanged();
                    break;
            }
        }
    };
    private void sendRequestWithOKHttp() {
        HttpUtil.sendOKHttpRequest(url,new okhttp3.Callback(){

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                String responseDate = response.body().string();
                //解析服务器并返回数据
                parseJSONWithJSONObject(responseDate);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    private void parseJSONWithJSONObject(String jsonData) {
        if (jsonData != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonData);

                //获取数据中的code值，如果是1则正确
                String resultCode = jsonObject.getString("status");
                if (resultCode.equals("206")) {
                    //中断转圈圈等待动画
//                    waitdialog.interrupt();
                    Log.e("-------","interrupt");
                    //获取到json数据中里的data内容
                    JSONArray resultJsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < resultJsonArray.length(); i++) {
                        //循环遍历，获取json数据中data数组里的内容
                        JSONObject Object = resultJsonArray.getJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();
                        try {
                            String place = Object.getString("place");//路线
                            String numbers = Object.getString("numbers");//剩余空位
                            int driver_id = Object.getInt("driver_id");//车牌号
                            //转时间类型
                            int time_p = Object.getInt("time_p");//发车时间
                            Long time=new Long(time_p);
                            Date date = new Date(time*1000);//记得*1000
                            SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String dateString = formatter.format(date);
                            //分离字符串，日期/时间
                            String[] temp = null;
                            temp = dateString.split(" ");
                            String truedate = temp[0];
                            String turetime = temp[1];


                            map.put("driver_id", driver_id);
                            map.put("numbers", numbers);
                            map.put("place", place);
                            map.put("date", truedate);
                            map.put("time", turetime);
                            //保存到ArrayList集合中
                            list.add(map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    handler1.sendEmptyMessageDelayed(2, 10);
//                    handler.sendEmptyMessageDelayed(1, 100);

                }else{
                    handler1.sendEmptyMessageDelayed(1, 10);
                    Toast.makeText(getApplicationContext(), "没有找到匹配的车辆", Toast.LENGTH_SHORT).show();
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
                viewHolder.place = (TextView) convertView.findViewById(R.id.tvCargono);
                viewHolder.numbers = (TextView) convertView.findViewById(R.id.tvVariety);
                viewHolder.driver_id = (TextView) convertView.findViewById(R.id.tvMarkno);
                viewHolder.date = (TextView) convertView.findViewById(R.id.tvKgs);
                viewHolder.time = (TextView) convertView.findViewById(R.id.tvNet);
                viewHolder.riding = convertView.findViewById(R.id.riding);

                convertView.setTag(viewHolder);

            } else {
                //重新获取ViewHolder
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.place.setText("路线："+list.get(position).get("place").toString());
            viewHolder.numbers.setText("剩余空位："+list.get(position).get("numbers").toString());
            viewHolder.driver_id.setText("车辆信息："+list.get(position).get("driver_id").toString());
            viewHolder.date.setText("发车时间："+list.get(position).get("date").toString());
            viewHolder.time.setText("发车时间："+list.get(position).get("time").toString());
            //liewview乘车按钮实现
            viewHolder.riding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
/*
                    //测试用的
                    Intent intent = new Intent(SetLocation.this, Success_client.class);
                    startActivity(intent);
                    //测试用的

*/

                    //接口实现

                    url_choose = "http://62.234.128.12/driver/public/index.php/passenger/choice/d_id/123/numbers/"+
                            peoplenumber+"/place/"+location+"/id/9994";
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
                                        .url(url_choose)
                                        .build();
                                Response response = client.newCall(request).execute();
                                //得到服务器返回的数据后，调用parseJSONWithJSONObject进行解析
                                String responseData = response.body().string();
                                parseJSONWithJSONObject1(responseData);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        private void parseJSONWithJSONObject1(String responseData) {
                            if (responseData != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(responseData);

                                    //获取数据中的code值，如果是1则正确
                                    String resultCode = jsonObject.getString("status");
                                    if (resultCode.equals("211")) {
                                        Intent intent = new Intent(SetLocation.this, Success_own.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(SetLocation.this, False_own.class);
                                        startActivity(intent);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e("------------",e.toString());
                                }
                            }


                        }
                    }).start();

                }
            });


            return convertView;
        }
    }

    final static class ViewHolder {
        TextView place;
        TextView numbers;
        TextView driver_id;
        TextView date;
        TextView time;
        Button riding;

    }

    @Override
    public void onClick(View view) {

    }
}
