package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import detailbean.DetailBean;
import http.Httputil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linear;
    private Context context;
    private TextView textView;
    private List<DetailBean.ResultBean.DescriptionBean> mlist = new ArrayList<>();
    private String CONTENT_TEXT = "text";
    private String CONTENT_IMAGE = "image";
    private String url = "http://api.lanrenzhoumo.com/wh/common/leo_detail?leo_id=1355364603&session_id=00004044ec6f4f5b17e1f5a135802541c659d9&v=4";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initView();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        linear = (LinearLayout) findViewById(R.id.linear);
        initData();
    }

    private void initData() {
        Httputil.downLoad(url, new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                DetailBean detailBean = gson.fromJson(string, DetailBean.class);
                mlist = detailBean.getResult().getDescription();
                handler.sendEmptyMessage(1);
            }
        });




    }

    public void initView(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mlist.size(); i++) {
            if (mlist.get(i).getType().equals(CONTENT_TEXT)){
                TextView textView = new TextView(context);
                textView.setLayoutParams(params);
                textView.setText(mlist.get(i).getContent());
                textView.setTextSize(17);
                textView.setTextColor(Color.GRAY);
                linear.addView(textView);
            }else if (mlist.get(i).getType().equals(CONTENT_IMAGE)){
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 350);
                params1.setMargins(0,20,0,20);
                imageView.setLayoutParams(params1);

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.with(context).load(mlist.get(i).getContent()).into(imageView);
                linear.addView(imageView);
            }
        }
    }
}
