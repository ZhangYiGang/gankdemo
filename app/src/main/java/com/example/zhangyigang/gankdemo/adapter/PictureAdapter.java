package com.example.zhangyigang.gankdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhangyigang.gankdemo.R;
import com.example.zhangyigang.gankdemo.bean.PictureBean;
import com.example.zhangyigang.gankdemo.constant.Constant;
import com.example.zhangyigang.gankdemo.utils.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yigang zhang
 * @date 19-6-4
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {
    private PictureHolder mPictureHolder;
    private ArrayList<HashMap<String, Object>>  mData;
    private ItemClick itemClick;
    private Context mContext;
    private int initalNum = Constant.PICTURE_ADAPTER_INITUM_DATA_NUM;
    private int addNum = Constant.PICTURE_ADAPTER_ADD_DATA_NUM;
    private int mNowStart, mNowEnd;
    public PictureAdapter( Context context){
        mContext = context;
        initData();
    }
    public interface ItemClick{
         void setText(int position);
         void setOnclick(String bitmapUrl,boolean isLocal);
    }

    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }
    @NonNull
    @Override
    public PictureHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mPictureHolder = new PictureHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_picture,viewGroup,false));
//创建viewholder
        return mPictureHolder;
    }
    /*
    * 初始化data
    * */
    private void initData() {
//        mData = new ArrayList<String>();
        mData = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> itemData ;
        for (int i = 'A'; i < 'A'+initalNum; i++)
        {
            itemData = new  HashMap<String, Object>();
            itemData.put("pictureText", "" + (char) i);
            itemData.put("pictureUrl",null);
            itemData.put("pictureBitmap",null);
            mData.add(itemData);
//            mData.add("" + (char) i);
        }
        mNowStart = 0;
        mNowEnd =  0;
    }
    /*
    * 这个函数是为了刷新时确定上下界
    * */
    public void flushBitmapPullup(PictureBean[] pictureBeans){
        HashMap<String, Object> mapData ;
        mNowEnd = mNowStart;
//        ArrayList <HashMap<String, Object>>  changeData = new ArrayList<HashMap<String,Object>>();
        for (PictureBean pictureBean: pictureBeans) {
            mapData = (HashMap<String, Object>) mData.get(mNowEnd);
            mNowEnd++;
            mapData.put("pictureBitmap", pictureBean.getmPictureBitmap());
            mapData.put("pictureUrl",pictureBean.getmPictureUrl());
//            changeData.add(mapData);
        }

        notifyItemRangeChanged(mNowStart,mNowEnd-mNowStart);
//    notifyDataSetChanged();
    }
/*
* 以下方法只有在调用 notifyItemChanged(index)方法时才会生效
* */
    @Override
    public void onBindViewHolder(@NonNull PictureHolder pictureHolder, int position, List<Object> payloads) {

        if (payloads.isEmpty()) {
//            payload为空,说明不需要改变
            onBindViewHolder(pictureHolder, position);
        } else {
            HashMap<String, Object> mapData = (HashMap<String, Object>) (payloads.get(position));
            String stringInfo= (String) mapData.get("pictureText");
            Bitmap bitmap = (Bitmap) mapData.get("pictureBitmap");
            if (stringInfo != null) {
//                holder.mPbDownProgress.setProgress(appInfoBean.getProgress());
                pictureHolder.textView.setText(stringInfo);
            }
            if (bitmap != null){
                pictureHolder.ivAdapter.setImageBitmap(bitmap);
            }
        }
    }
    @Override
    public void onBindViewHolder(@NonNull PictureHolder pictureHolder, final int position) {
        HashMap<String, Object> mapData = (HashMap<String, Object>) mData.get(position);
        String stringInfo= (String) mapData.get("pictureText");
        Bitmap bitmap = (Bitmap) mapData.get("pictureBitmap");
        String bitmapUrl = (String)mapData.get("pictureUrl");
        final  boolean isLocal ;
//        todo 这里后面可以添加一个函数 bind（data)
        if (bitmap != null){
            pictureHolder.ivAdapter.setImageBitmap(bitmap);
            isLocal = false;
        }
        else{
            pictureHolder.ivAdapter.setImageBitmap(ImageLoader.getDrableImage(mContext,"isloading"));
            isLocal = true;
        }
        pictureHolder.textView.setText(stringInfo);
        if (itemClick!=null){
            pictureHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.setText(position);
                }
            });
            pictureHolder.ivAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.setOnclick(bitmapUrl,isLocal);
                }
            });
        }

    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class PictureHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_num)
        public TextView textView;
        @BindView(R.id.iv_adapter)
        public ImageView ivAdapter;
        public PictureHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);



        }

    }


}
