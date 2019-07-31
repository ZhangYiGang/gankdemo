package com.example.zhangyigang.gankdemo.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhangyigang.gankdemo.R;
import com.example.zhangyigang.gankdemo.constant.Constant;
import com.example.zhangyigang.gankdemo.customizeView.ZoomView;
import com.example.zhangyigang.gankdemo.utils.HttpClientUtils;
import com.example.zhangyigang.gankdemo.utils.ImageLoader;
import com.example.zhangyigang.gankdemo.utils.MyLruCache;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleActivity extends AppCompatActivity {

    @BindView(R.id.zoom_view)
    public ZoomView zoomView;
    private MyLruCache<String, Bitmap> myLruCache;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constant.ORIGIN_PICTURE:
                    zoomView.setImageBitmap((Bitmap)msg.obj);
                    Toast.makeText(SingleActivity.this,"图片已经更换为原始大图",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        ButterKnife.bind(this);
        myLruCache = PictureFragemnt.mLruCache;
        setview();


    }

    private void setview() {
        String imageUrl = getIntent().getStringExtra("bitmapUrl");
        boolean isLocal = getIntent().getBooleanExtra("isLocal",false);
        if (imageUrl!=null&& isLocal==false){
            zoomView.setImageBitmap((Bitmap) myLruCache.get(imageUrl));
            new Thread(()->{
                InputStream inputStream = HttpClientUtils.getInstance().httpGetStream(imageUrl);
                Bitmap bitmap= ImageLoader.loadBitmap(inputStream,true);
                Message message = new Message();
                message.obj = bitmap;
                message.what = Constant.ORIGIN_PICTURE;
                mHandler.sendMessage(message);
//            大尺寸图片在这里不放进cache里面，因为占用过多缓存
            }).start();
        }
        else {

            zoomView.setImageBitmap(ImageLoader.getDrableImage(this,"isloading"));
        }

    }

}
