package com.example.zhangyigang.gankdemo.utils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * @author yigang zhang
 * @date 19-7-13
 * 这个类是为了判断是否成功获取到response,还是因为超时
 * response即为okhttp获取到的对象
 * isDone是网络请求是否完成
 * responseFlag网络请求是否成功
 */
public class MyResponse {
    private Response response  = null ;
    private  boolean responseFlag ;
    private boolean  isDone ;

    public MyResponse(){
        this.responseFlag = false;
        this.isDone = false;
        this.response = null;
    }

    public void setResponse(Response response) {
        this.isDone = true;
        this.responseFlag = true;
        this.response = response;

    }

    public boolean getResponseStatus() {
        return this.isDone;
    }

    public void setResponseOnFail() {
        this.isDone = true;
        this.responseFlag = false;
        this.response = null;
    }

    public Response getResponse() {
        return this.response;
    }

    public String getResponseString() {
        try {
            return this.response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    public InputStream getResponseStream() {
        return this.response.body().byteStream();
    }
}
