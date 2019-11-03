package com.example.a98189.taxi;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static void sendOKHttpRequest(final String address, final okhttp3.Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建OkHttpClient实例
                    OkHttpClient client = new OkHttpClient();
                    //post请求
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username","chen")
                            .add("passwork","123456")
                            .build();
                    //创建Request对象，发一条HTTP请求
                    Request request = new Request
                            .Builder()
                            .post(requestBody)
                            .url(address)
                            .build();
                    client.newCall(request).enqueue(callback);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }).start();
    }
}
