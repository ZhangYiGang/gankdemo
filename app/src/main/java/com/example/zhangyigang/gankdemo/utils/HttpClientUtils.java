package com.example.zhangyigang.gankdemo.utils;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.example.zhangyigang.gankdemo.constant.Constant;
import com.example.zhangyigang.gankdemo.task.PictureAsycTask;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author yigang zhang
 * @date 19-7-9
 */
public class HttpClientUtils {
    private static HttpClientUtils httpClientUtils = null;
    private  OkHttpClient okHttpClient = null;
//    private  Response response  = null ;
//    private  boolean getResponseFlag = true;
    public static HttpClientUtils getInstance() {

        if (httpClientUtils == null) {
            httpClientUtils = new HttpClientUtils();

        }
        return httpClientUtils;

    }
    public HttpClientUtils(){
        okHttpClient =  new OkHttpClient();
    }
/*
* 封装的http get请求,返回得到的response
* */
    private MyResponse httpGet(String url){
        MyResponse myResponse = new MyResponse();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myResponse.setResponseOnFail();
                Log.d("okhttp", "onFailure: "+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myResponse.setResponse(response);
                Log.d("okhttp", "success ");

            }
        });
        return myResponse;
    }



    public String httpGetString(String url){
        MyResponse myResponse = httpGet(url);
        while(!myResponse.getResponseStatus()){
            int count = 0;
            boolean timeOut = false;
            timeOut = TimeUtils.sleepAndExceed(1,count, 5);
            if (timeOut)
                return "";
            count ++;
        }

        return myResponse.getResponseString();
    }

    public InputStream httpGetStream(String url){
        MyResponse myResponse = httpGet(url);
        while(!myResponse.getResponseStatus()){
            int count = 0;
            boolean timeOut = false;
            timeOut = TimeUtils.sleepAndExceed(1,count, 5);
            if (timeOut)
                return null;
            count ++;
        }
        return myResponse.getResponseStream();
    }

}
