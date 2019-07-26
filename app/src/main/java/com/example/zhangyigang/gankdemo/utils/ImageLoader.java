package com.example.zhangyigang.gankdemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.zhangyigang.gankdemo.constant.Constant;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream; /**
 * @author yigang zhang
 * @date 19-7-17
 * 用来加载图片，以及图片缓存
 */
public class ImageLoader {

    private  static int reqWidth = Constant.PICTURE_WIDTH;
    private static int reqHeight = Constant.PICTURE_HEIGHT;
    public static Bitmap loadBitmap(InputStream inputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//        以下图片获取方式为只获取图片大小
        BufferedInputStream buffer=new BufferedInputStream(inputStream);
        try {
//            Log.d("picture_size", String.valueOf(buffer.available()));
//            只获取到1m大小
            buffer.mark(1024*1024);
            BitmapFactory.decodeStream(buffer,null,options);

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            buffer.reset();
//            读了两次数据，要先回到最开始的位置，否则读的流是0
        } catch (IOException e) {
            e.printStackTrace();
        }


        return BitmapFactory.decodeStream(buffer,null,options);
    }
/*
* 计算图片应该压缩的大小
* */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    public void decodeBytearray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int temp =0;
        while ((temp= inputStream.read(buffer))!=-1){
            byteArrayOutputStream.write(buffer,0,temp);
        }
        byte[] data = byteArrayOutputStream.toByteArray();
        BitmapFactory.decodeByteArray(data,0,data.length,null);
    }
}
