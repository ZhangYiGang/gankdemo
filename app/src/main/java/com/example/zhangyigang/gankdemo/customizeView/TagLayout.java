package com.example.zhangyigang.gankdemo.customizeView;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.zhangyigang.gankdemo.R;
import com.example.zhangyigang.gankdemo.customizeView.PictureTagView;
import com.example.zhangyigang.gankdemo.customizeView.PictureTagView.Status;
import com.example.zhangyigang.gankdemo.customizeView.PictureTagView.Direction;

/**
 * 添加标签
 * @author yigang zhang
 * @date 19-7-31
 */
public class TagLayout extends LinearLayout implements View.OnTouchListener {
    private View touchView,clickView;
    private int startX,startY,startTouchViewLeft,startTouchViewTop;
    private static final int CLICKRANGE = 5;
    public TagLayout(Context context) {
        super(context,null);

        initView();
    }

    private void initView() {

        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchView = null;
                if(clickView!=null){
//                    如果已经点过，则重新更新位置,将click置空
                    ((PictureTagView)clickView).setStatus(Status.Normal);
                    clickView = null;
                }
                startX = (int) event.getX();
                startY = (int) event.getY();
                if(hasView(startX,startY)){
                    startTouchViewLeft = touchView.getLeft();
                    startTouchViewTop = touchView.getTop();
                }
                else{
                    addItem(startX,startY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveView((int) event.getX(),
                        (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                int endX = (int) event.getX();
                int endY = (int) event.getY();
                //如果挪动的范围很小，则判定为单击
                if(touchView!=null&&Math.abs(endX - startX)<CLICKRANGE&&Math.abs(endY - startY)<CLICKRANGE){
                    //当前点击的view进入编辑状态
                    ((PictureTagView)touchView).setStatus(Status.Edit);
                    clickView = touchView;
                }
                touchView = null;
                break;
        }
        return false;
    }

    private void moveView(int x, int y) {

    }

    private void addItem(int x, int y) {
        View view = null;
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        if(x>getWidth()*0.5){
            params.leftMargin = x - PictureTagView.getViewWidth();
            view = new PictureTagView(getContext(),Direction.Right);
        }
        else{
            params.leftMargin = x;
            view = new PictureTagView(getContext(),Direction.Left);
        }

        params.topMargin = y;
        //上下位置在视图内
        if(params.topMargin<0)params.topMargin =0;
        else if((params.topMargin+PictureTagView.getViewHeight())>getHeight())params.topMargin = getHeight() - PictureTagView.getViewHeight();


        this.addView(view, params);
    }


    private boolean hasView(int x,int y){
        //循环获取子view，判断xy是否在子view上，即判断是否按住了子view
        for(int index = 0; index < this.getChildCount(); index ++){
            View view = this.getChildAt(index);
            int left = (int) view.getX();
            int top = (int) view.getY();
            int right = view.getRight();
            int bottom = view.getBottom();
            Rect rect = new Rect(left, top, right, bottom);
            boolean contains = rect.contains(x, y);
            //如果是与子view重叠则返回真,表示已经有了view不需要添加新view了
            if(contains){
                touchView = view;
//                改变touchview的z轴，将其放在最顶端
                touchView.bringToFront();
                return true;
            }
        }
        touchView = null;
        return false;
    }
}
