package com.example.zhangyigang.gankdemo.customizeView;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhangyigang.gankdemo.R;

/**
 * @author yigang zhang
 * @date 19-7-31
 */
public class PictureTagView extends RelativeLayout {
    private Context context;

    public enum Status{Normal,Edit};
    public enum Direction{Left,Right}
    private Direction direction = Direction.Left;
    private Status status = Status.Normal;
    private static final int ViewWidth = 80;
    private static final int ViewHeight = 50;
    private TextView tv_tag_label;
    private EditText et_tag_label;
    public PictureTagView(Context context, Direction direction) {
        super(context);
        this.context = context;
        this.direction = direction;
        initViews();
        init();
        initEvents();
    }

    private void init() {
    }

    private void initEvents() {
    
    }

    private void initViews() {
        LayoutInflater.from(context).inflate(R.layout.item_picture, this,true);
    }


    public PictureTagView(Context context) {
        super(context);
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public static int getViewWidth(){
        return ViewWidth;
    }
    public static int getViewHeight(){
        return ViewHeight;
    }
}
