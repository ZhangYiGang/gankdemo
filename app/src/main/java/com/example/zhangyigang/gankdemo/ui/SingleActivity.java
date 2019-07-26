package com.example.zhangyigang.gankdemo.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.zhangyigang.gankdemo.R;
import com.example.zhangyigang.gankdemo.customizeView.ZoomView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleActivity extends AppCompatActivity {

    @BindView(R.id.zoom_view)
    public ZoomView zoomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        ButterKnife.bind(this);
        zoomView.setImageBitmap();

    }

}
