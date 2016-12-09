package com.hjq.okhttputil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.post)
    Button post;
    @Bind(R.id.get)
    Button get;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    public String url = "https://gank.io";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.post, R.id.get})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post:

                break;
            case R.id.get:
                
                break;
        }
    }
}
