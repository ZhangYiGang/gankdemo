package com.example.zhangyigang.gankdemo.ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;
import com.example.zhangyigang.gankdemo.R;
import com.example.zhangyigang.gankdemo.adapter.MyDecoration;
import com.example.zhangyigang.gankdemo.adapter.PictureAdapter;
import com.example.zhangyigang.gankdemo.bean.PictureBean;
import com.example.zhangyigang.gankdemo.constant.Constant;
import com.example.zhangyigang.gankdemo.task.PictureAsycTask;
import com.example.zhangyigang.gankdemo.task.UrlAsycTask;
import com.example.zhangyigang.gankdemo.utils.MyLruCache;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PictureFragemnt.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PictureFragemnt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PictureFragemnt extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String [] mUrlArray;
    @BindView(R.id.rv_picture)
    public RecyclerView mRecyclerView;
    @BindView(R.id.rl_picture)
    public SwipeRefreshLayout mRefreshLayout= null;
    public PictureAdapter mPictureAdapter =null;
    public PictureAsycTask mPictureAsycTask = null;
    private OnFragmentInteractionListener mListener = null;
    public static MyLruCache<String, Bitmap> mLruCache = null;
    static {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        Log.d("max_memory", String.valueOf(maxMemory));
        int cacheSize = maxMemory / 8;
        // 设置图片缓存大小为程序最大可用内存的1/8,经测试，大概在100m左右,我设置本机内存在2g左右时
        mLruCache = new MyLruCache<String, Bitmap>(cacheSize);
    }

    private  Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message message){
            switch (message.what){
                case Constant.HANDLER_PICTUREURL_WHAT:
                    PictureBean[] pictureBeans = (PictureBean[]) message.obj;
                    mPictureAdapter.flushBitmapPullup(pictureBeans);
                    break;
//                    PictureFragemnt.this.mPictureAdapter.setBitmap(bitmap);
                case Constant.HANDLER_PICTURE_URL_WHAT:
                     mUrlArray = (String[]) message.obj;

            }
        }
    };
    public PictureFragemnt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PictureFragemnt.
     */
    // TODO: Rename and change types and number of parameters
    public static PictureFragemnt newInstance(String param1, String param2) {
        PictureFragemnt fragment = new PictureFragemnt();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_picture, container, false);
        ButterKnife.bind(this,inflate);
        initUrlData();
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));//设置layout
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (mUrlArray != null){
                            mPictureAsycTask = new PictureAsycTask(mHandler, PictureFragemnt.this.getActivity());
                            mPictureAsycTask.execute(mUrlArray);
//                        todo 在这里要等待完成后再取消转
                            mRefreshLayout.setRefreshing(false);
                        }
                        initUrlData();
                    }

                });
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        mPictureAdapter = new PictureAdapter( this.getContext());
        mPictureAdapter.setItemClick(new PictureAdapter.ItemClick() {
            @Override
            public void setText(int position) {
                Toast.makeText(PictureFragemnt.this.getContext(),"这是"+position+"个",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void setOnclick(String bitmapUrl,boolean isLocal) {
                Intent newIntent = new Intent(PictureFragemnt.this.getActivity(),SingleActivity.class);
                newIntent.putExtra("bitmapUrl", bitmapUrl);
                newIntent.putExtra("isLocal",isLocal);
                startActivity(newIntent);
            }
        });
        mRecyclerView.setAdapter(mPictureAdapter);//设置adapter
//        mRecyclerView.addItemDecoration(new MyDecoration(this.getContext(),MyDecoration.VERTICAL_LIST));

        return inflate;
    }

    private void initUrlData() {
        UrlAsycTask urlAsycTask =  new UrlAsycTask(mHandler,PictureFragemnt.this.getActivity());
        urlAsycTask.execute(Constant.TYPE_ASYNC_PICTURE_URL);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
