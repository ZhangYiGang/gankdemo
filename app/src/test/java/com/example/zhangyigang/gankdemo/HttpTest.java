package com.example.zhangyigang.gankdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.zhangyigang.gankdemo.utils.HttpClientUtils;

import org.junit.Test;

import java.io.InputStream;
import java.util.Calendar;

/**
 * @author yigang zhang
 * @date 19-7-13
 */
public class HttpTest {
    @Test
    public void testThread(){
        HttpClientUtils httpClientUtils = HttpClientUtils.getInstance();
        String [] imageUrlArray = new String[]{"https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg","https://ws1.sinaimg.cn/large/0065oQSqgy1fy58bi1wlgj30sg10hguu.jpg","https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg"};
        for (String imageUrl:imageUrlArray) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.print( "线程开始"+Thread.currentThread().getName()+"   "+String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND))+"\n");
//                    InputStream inputStream = httpClientUtils.httpGetStream(imageUrl);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    System.out.print("线程结束"+String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND))+"\n");
                }
            }).start();

        }
    }
}
