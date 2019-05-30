package com.example.zhangyigang.gankdemo.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    public static int PAGE_NUM = 2;
    private VideoFragment videoFragment=null;
    private PictureFragemnt pictureFragemnt = null;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        videoFragment = new VideoFragment();
        pictureFragemnt = new PictureFragemnt();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment = videoFragment;
                break;
            case 1:
                fragment = pictureFragemnt;
                break;
            case 2:
                fragment = videoFragment;
                break;
            case 3:
                fragment = pictureFragemnt;
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }


}
