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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yigang zhang
 * @date 19-6-4
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {
    private PictureHolder mPictureHolder;
    private ArrayList<String> mData; 
    @NonNull
    @Override
    public PictureHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mPictureHolder = new PictureHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_picture,viewGroup,false));
        initData();
        return mPictureHolder;
    }

    private void initData() {
        for (int i = 'A'; i < 'G'; i++)
        {
            mData.add("" + (char) i);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PictureHolder pictureHolder, int position) {
        mPictureHolder.textView.setText(mData.get(position));
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
            ButterKnife.bind(itemView);

        }
    }
}
