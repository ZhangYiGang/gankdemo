package com.example.zhangyigang.gankdemo.utils;

/**
 * @author yigang zhang
 * @date 19-7-9
 */
public class StringUtils {

    //判断字符串非空（2个条件）：
    //1.引用非空-null
    //2.非空字符串-" "
    public static boolean hasLength(String str) {
        return str != null && !"".equals(str.trim());

    }
}
