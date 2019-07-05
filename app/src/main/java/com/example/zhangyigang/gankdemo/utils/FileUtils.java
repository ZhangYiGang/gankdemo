package com.example.zhangyigang.gankdemo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author yigang zhang
 * @date 19-7-3
 */
public class FileUtils {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static void writeToFile(String content, String... fileargs) {
        String fileName = "info.txt";
        String dirPath = "test";
        if (fileargs.length == 1) {
            fileName = fileargs[0];
        } else if (fileargs.length == 2) {
            fileName = fileargs[0];
            dirPath = fileargs[1];
        }

//判断当前sdcard内是否可读写
//            File sdCard=Environment.getExternalStorageDirectory();
        File dirName = getAlbumStorageDir(dirPath);
        try {
//                    getCan.. 获取当前文件的绝对路径
            File targetFile = new File(dirName.getCanonicalPath(), fileName);
            if (!targetFile.exists())
                targetFile.createNewFile();
            RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
            raf.seek(targetFile.length());
//                    这种方式是追加的形式
            raf.write(content.getBytes());
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static File getAlbumStorageDir(String albumName) {
//        Environment.getExternalStorageDirectory();按这个是获取到sd卡的路径
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.d("file", "Directory not create");
        }
        return file;
    }

    public static boolean isExternalStorageWritable(Activity context) {
        String state = Environment.getExternalStorageState();
        if (Build.VERSION.SDK_INT >= 23) {

            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(context, PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE);
                return false;
            }
            return true;
        } else {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
            }
            return false;
        }
    }
}


