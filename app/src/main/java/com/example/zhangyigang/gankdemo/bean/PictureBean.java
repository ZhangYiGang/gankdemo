package com.example.zhangyigang.gankdemo.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class PictureBean implements Parcelable {
    private String mPictureInfo;
    private String mPictureUrl;
    private Bitmap mPictureBitmap;
    protected PictureBean(Parcel in) {
        PictureBean pictureBean = new PictureBean();
        pictureBean.setmPictureInfo(in.readString());
        pictureBean.setmPictureUrl(in.readString());
        pictureBean.setmPictureBitmap(null);
    }
    public PictureBean(){

    }

    public String getmPictureInfo(){
        return this.mPictureInfo;
    }
    public String getmPictureUrl(){
        return this.mPictureUrl;
    }

    public Bitmap getmPictureBitmap() {
        return mPictureBitmap;
    }

    public void setmPictureBitmap(Bitmap mPictureBitmap) {
        this.mPictureBitmap = mPictureBitmap;
    }

    public void setmPictureInfo(String mPictureInfo) {
        this.mPictureInfo = mPictureInfo;
    }
    public void setmPictureUrl(String mPictureUrl){
        this.mPictureUrl = mPictureUrl;
    }

    public static final Creator<PictureBean> CREATOR = new Creator<PictureBean>() {
        @Override
        public PictureBean createFromParcel(Parcel in) {
            return new PictureBean(in);
        }

        @Override
        public PictureBean[] newArray(int size) {
            return new PictureBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPictureInfo);
        dest.writeString(mPictureUrl);

    }
}
