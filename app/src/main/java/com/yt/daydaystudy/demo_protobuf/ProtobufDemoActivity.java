package com.yt.daydaystudy.demo_protobuf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.yt.daydaystudy.R;
import com.yt.daydaystudy.demo_recyclerview.DemoAdapter;
import com.yt.daydaystudy.demo_recyclerview.GalleryItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/10/25.
 */
public class ProtobufDemoActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity,ProtobufDemoActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_demo);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        //使recyclerview吗，每次滑动结束后，保持在正中间
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        //设置item间隔
        mRecyclerView.addItemDecoration(new GalleryItemDecoration());
        initData();
    }

    private void initData() {
        List<String> data = new ArrayList<>();
        for (int i = 0;i<10000;i++){
            data.add("测试"+i);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new DemoAdapter(data));
    }

}
