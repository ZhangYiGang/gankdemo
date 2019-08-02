package com.example.zhangyigang.gankdemo.ui;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhangyigang.gankdemo.R;
import com.example.zhangyigang.gankdemo.adapter.FragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.rg_tab_bar)
    public RadioGroup radioGroup;
    @BindView(R.id.button_page_1)
    public  RadioButton radioButton1;
    @BindView(R.id.button_page_2)
    public RadioButton radioButton2;
    @BindView(R.id.button_page_3)
    public RadioButton radioButton3;
    @BindView(R.id.button_page_4)
    public RadioButton radioButton4;
    @BindView(R.id.vp_page)
    public ViewPager viewPager;
    private FragmentAdapter fragmentAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bindViews();

    }

    private void bindViews() {
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        Log.d("null reference",fragmentAdapter.toString());
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.button_page_1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.button_page_2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.button_page_3:
                viewPager.setCurrentItem(0);
                break;
            case R.id.button_page_4:
                viewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state==2){
//            s
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                switch (permissions[0]){
                    case Manifest.permission.READ_CONTACTS://权限1

                        break;
                    case Manifest.permission.CALL_PHONE://权限2
                        break;
                    default:
                        Log.d("Mainactivity","权限");
                }
                break;
            default:
        }
    }

}
