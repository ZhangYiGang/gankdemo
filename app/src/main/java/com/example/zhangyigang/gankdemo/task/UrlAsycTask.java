package com.example.zhangyigang.gankdemo.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.zhangyigang.gankdemo.constant.Constant;
import com.example.zhangyigang.gankdemo.utils.HttpClientUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class UrlAsycTask extends AsyncTask {
    private Handler mHandler;
    private Activity mActivity;
    public UrlAsycTask(Handler handler, Activity context){
        mHandler = handler;
        mActivity = context;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        int type = (int) objects[0];
        Message handlerMessage =new Message();
        switch (type){
            case Constant.TYPE_ASYNC_PICTURE_URL:
                String[] urlArray= parseUrl();
                handlerMessage.obj = urlArray;
                handlerMessage.what = Constant.HANDLER_PICTURE_URL_WHAT ;
                mHandler.sendMessage(handlerMessage);
        }
        return null;
    }

    private String[] parseUrl() {
        int page_num = new Random().nextInt(5);
        String url = "http://gank.io/api/data/福利/10/"+page_num;
        HttpClientUtils httpClientUtils = HttpClientUtils.getInstance();
        String jsonContent = httpClientUtils.httpGetString(url);
        String [] imageUrlArray = parsePictureJson(jsonContent);
        return imageUrlArray;
    }

    private String[] parsePictureJson(String jsonContent) {
        ArrayList<String > urlArray= new ArrayList<String>();
        Log.d("now_time", String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
        try {
            JSONObject imageObject = new JSONObject(jsonContent);
            JSONArray array = imageObject.getJSONArray("results");
            for(int i=0;i<Constant.PICTURE_ADAPTER_INITUM_DATA_NUM;i++){
                imageObject = array.getJSONObject(i);
                urlArray.add( imageObject.getString("url"));
            }
            return  urlArray.toArray(new String[urlArray.size()]);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
