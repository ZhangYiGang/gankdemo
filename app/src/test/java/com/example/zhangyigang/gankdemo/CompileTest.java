package com.example.zhangyigang.gankdemo;

import android.util.Log;

import org.junit.Test;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author yigang zhang
 * @date 19-6-27
 */
public class CompileTest {
    @Test
    public void testUrl(){
        System.out.print("failed1");
//        String imageUrl = "https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1561469619812_R&pv=&ic=&nc=1&z=&hd=&latest=&copyright=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&sid=&word=%E9%BE%99%E7%8F%A0";
       String imageUrl = "https://blog.csdn.net/qq_20082961/article/details/52793501";
        Pattern p=Pattern.compile("hoverURL\":\"[^\"]+");
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(imageUrl)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.print("failed");
                Log.d("pictureTask", "onFailure: "+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                System.out.print(response.body().string());
                System.out.print("success");
                Log.d("pictureTask", "onResponse: " + response.body().string());

            }
        });
    }
}
