package com.example.zhangyigang.gankdemo.task;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;

import com.example.zhangyigang.gankdemo.constant.Constant;
import com.example.zhangyigang.gankdemo.utils.HttpClientUtils;
import com.example.zhangyigang.gankdemo.utils.ImageLoader;
import com.example.zhangyigang.gankdemo.utils.MyLruCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author yigang zhang
 * @date 19-6-18
 */
public class PictureAsycTask extends AsyncTask {
    private Handler mHandler =null;
    private Activity mActivity = null;
    private static  LruCache<String, Bitmap> mLruCache = null;


    public PictureAsycTask(Handler handler, Activity context) {
        mHandler = handler;
        mActivity = context;

    }
    @Override
    protected Object doInBackground(Object[] objects) {
        String []  urlArray = Arrays.copyOf((String[]) objects,objects.length);
        getImageFromUrl(urlArray);
        return null;
    }

    private void getImageFromUrl(String[] imageUrlArray) {
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
        ArrayList<Bitmap > urlArray= new ArrayList<Bitmap>();
        int thread_length = imageUrlArray.length>Constant.PICTURE_THREAD_NUM?Constant.PICTURE_THREAD_NUM:imageUrlArray.length;
        ExecutorService es = Executors.newFixedThreadPool(thread_length);
        List<Callable<Object>> todo = new ArrayList<Callable<Object>>(thread_length);
        for (String imageUrl:imageUrlArray) {
            todo.add(Executors.callable( new Thread(()-> {
//                Log.d("now_time", "图片请求线程开始"+Thread.currentThread().getName()+"   "+String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
                Bitmap bitmap = loadBitmapFromUrl(imageUrl);
                urlArray.add(bitmap);
            })));
        }
        try {
            es.invokeAll(todo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return urlArray.toArray(new Bitmap[urlArray.size()]);
    }

    private Bitmap loadBitmapFromUrl(String imageUrl) {
        Bitmap bitmap = mLruCache.get(imageUrl);
        if (bitmap == null) {
            HttpClientUtils httpClientUtils = HttpClientUtils.getInstance();
            InputStream inputStream = httpClientUtils.httpGetStream(imageUrl);
            bitmap = ImageLoader.loadBitmap(inputStream);
            Log.d("picture_size", "图片加载" + Thread.currentThread().getName() + "   " + bitmap.getByteCount());
//                Log.d("now_time", "图片请求线程结束"+Thread.currentThread().getName()+"   "+String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
            mLruCache.put(imageUrl,bitmap);
           }
        return bitmap;
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

