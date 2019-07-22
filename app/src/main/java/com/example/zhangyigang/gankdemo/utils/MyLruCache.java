package com.example.zhangyigang.gankdemo.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

public class MyLruCache<K,V> extends LruCache<K,V> {

    public MyLruCache(int maxSize) {
        super(maxSize);
    }
    @Override
    protected int sizeOf(K key, V value) {
        if(value instanceof Bitmap){
            return ((Bitmap)value).getByteCount();
        }
        return super.sizeOf(key,value);
    }
}
