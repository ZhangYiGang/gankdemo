package com.example.zhangyigang.gankdemo.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @author yigang zhang
 * @date 19-6-18
 */
public class PictureAsycTask extends AsyncTask {
    private Handler mHandler =null;
    public PictureAsycTask(Handler handler){
        mHandler = handler;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("pictureTask", "onFailure: "+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("pictureTask", "onResponse: " + response.body().string());
            }
        });
        return null;
    }
}
