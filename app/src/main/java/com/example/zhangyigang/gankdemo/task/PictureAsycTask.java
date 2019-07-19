package com.example.zhangyigang.gankdemo.task;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.zhangyigang.gankdemo.constant.Constant;
import com.example.zhangyigang.gankdemo.utils.FileUtils;
import com.example.zhangyigang.gankdemo.utils.HttpClientUtils;
import com.example.zhangyigang.gankdemo.utils.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;


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
        getImageFromUrl(url);
        return null;
    }

    private void getImageFromUrl(String content) {
        HttpClientUtils httpClientUtils = HttpClientUtils.getInstance();
        String jsonContent = httpClientUtils.httpGetString(content);
        String [] imageUrlArray = parseJson(jsonContent);
        Bitmap [] bitmapArray = parseUrl(imageUrlArray);

        Message message = new Message();
//        Bundle bundle = new Bundle();
//        bundle.putString("url",imageUrl);
//        message.setData(bundle);
//        以上是传递bundle的信息
        message.what = Constant.HANDLER_PICTUREURL_WHAT;
        message.obj = bitmapArray;
        PictureAsycTask.this.mHandler.sendMessage(message);

//        if (FileUtils.isExternalStorageWritable(mActivity)){
//            FileUtils.writeToFile(content);
////                    这个是测试是否能写入文件的
//        }
    }

    private Bitmap[] parseUrl(String[] imageUrlArray) {
        Log.d("now_time", String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
        HttpClientUtils httpClientUtils = HttpClientUtils.getInstance();
        ArrayList<Bitmap > urlArray= new ArrayList<Bitmap>();
        ArrayList<Thread> waitThread = new ArrayList<Thread>();
        for (String imageUrl:imageUrlArray) {
            Thread pictureThread = new Thread(()-> {

                Log.d("now_time", "图片请求线程开始"+Thread.currentThread().getName()+"   "+String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
                InputStream inputStream = httpClientUtils.httpGetStream(imageUrl);
                Bitmap bitmap = ImageLoader.loadBitmap(inputStream);
                Log.d("picture_size","图片大小为"+bitmap.getByteCount());
                Log.d("now_time", "图片请求线程结束"+Thread.currentThread().getName()+"   "+String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
                urlArray.add(bitmap);
            });
            pictureThread.start();
            waitThread.add(pictureThread);
        }
//        waitThread.stream().
        Bitmap bitmap = BitmapFactory.decodeStream(httpClientUtils.httpGetStream(imageUrlArray[0]));
        Log.d("picture_size","图片大小为"+bitmap.getByteCount());
        Log.d("now_time", String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
        return urlArray.toArray(new Bitmap[urlArray.size()]);
    }

    private String[] parseJson(String content) {
        ArrayList<String > urlArray= new ArrayList<String>();
        Log.d("now_time", String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
        try {
            JSONObject imageObject = new JSONObject(content);
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

