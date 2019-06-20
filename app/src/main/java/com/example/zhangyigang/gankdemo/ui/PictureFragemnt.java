package com.example.zhangyigang.gankdemo.ui;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import com.example.zhangyigang.gankdemo.R;
import com.example.zhangyigang.gankdemo.adapter.MyDecoration;
import com.example.zhangyigang.gankdemo.adapter.PictureAdapter;
import com.example.zhangyigang.gankdemo.task.PictureAsycTask;

import java.util.ArrayList;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> mData;
    @BindView(R.id.rv_picture)
    public RecyclerView mRecyclerView;
    @BindView(R.id.rl_picture)
    public SwipeRefreshLayout mRefreshLayout= null;
    public PictureAdapter mPictureAdapter =null;
    public PictureAsycTask mPictureAsycTask = null;
    private OnFragmentInteractionListener mListener = null;
    private static Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message message){

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

    private void initData() {
        mData = new ArrayList<String>();
        for (int i = 'A'; i < 'G'; i++)
        {
            mData.add("" + (char) i);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_picture, container, false);
        ButterKnife.bind(this,inflate);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));//设置layout
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPictureAsycTask = new PictureAsycTask(mHandler);
                        mPictureAsycTask.execute();
                        mRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        initData();
        mRecyclerView.setAdapter(mPictureAdapter = new PictureAdapter(mData));//设置adapter
//        mRecyclerView.addItemDecoration(new MyDecoration(this.getContext(),MyDecoration.VERTICAL_LIST));
        mPictureAdapter.setItemClick(new PictureAdapter.ItemClick() {
            @Override
            public void setText(int position) {
                Toast.makeText(PictureFragemnt.this.getContext(),"这是"+position+"个",Toast.LENGTH_SHORT).show();
            }
        });
        return inflate;
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
