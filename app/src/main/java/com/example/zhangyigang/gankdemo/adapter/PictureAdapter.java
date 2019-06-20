package com.example.zhangyigang.gankdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangyigang.gankdemo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yigang zhang
 * @date 19-6-4
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {
    private PictureHolder mPictureHolder;
    private List<String> mData;
    private ItemClick itemClick;
    public PictureAdapter(List<String> data){
        mData = data;
    }

    public interface ItemClick{
        public void setText(int position);
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


    @Override
    public void onBindViewHolder(@NonNull PictureHolder pictureHolder, final int position) {
        String text = mData.get(position);
//        绑定数据
//        todo 这里后面可以添加一个函数 bind（data)
        mPictureHolder.textView.setText(mData.get(position));
        if (itemClick!=null){
            mPictureHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.setText(position);
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
        public PictureHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
