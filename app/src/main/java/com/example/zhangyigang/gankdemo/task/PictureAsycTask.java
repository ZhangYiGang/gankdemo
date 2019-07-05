package com.example.zhangyigang.gankdemo.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.zhangyigang.gankdemo.utils.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
    private Activity mActivity = null;
    public PictureAsycTask(Handler handler, Activity context){
        mHandler = handler;
        mActivity = context;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        String url = "http://gank.io/api/data/福利/10/1";
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
                String content =response.body().string();
                if (FileUtils.isExternalStorageWritable(mActivity)){
                    FileUtils.writeToFile(content);
                    Log.d("pictureTask", "onResponse: " + content);
                    String imageUrl = parseJson(content);
                    Message message = new Message();
//                    message.setData();
                    PictureAsycTask.this.mHandler.sendMessage();
                }


            }
        });
        return null;
    }

    private String parseJson(String content) {
        try {
            JSONObject imageObject = new JSONObject(content);
            String imageUrl = new JSONObject(imageObject.getString("results")).getString("url");
            return imageUrl;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

