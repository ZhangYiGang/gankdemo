package com.example.zhangyigang.gankdemo.utils;

/**
 * @author yigang zhang
 * @date 19-7-10
 */
public class TimeUtils {
    public static void sleep(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean sleepAndExceed(int sleepSecond,int passSecond, int seconds){
        try {
            Thread.sleep(sleepSecond*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (passSecond >seconds){
            return true;
        }
        return false;
    }
}
