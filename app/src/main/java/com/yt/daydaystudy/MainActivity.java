package com.yt.daydaystudy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.yt.daydaystudy.demo_arouter.ArouterDemoActivity;
import com.yt.daydaystudy.demo_cjs.CJSActivity;
import com.yt.daydaystudy.demo_greendao.GreenDaoActivity;
import com.yt.daydaystudy.demo_statusbarcolor.StatusBarActivity;
import com.yt.daydaystudy.demo_takephoto.PhotoUploadActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements CommonAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //使用FlexboxLayoutManager搭配RecyclerView实现流式布局
        mRecyclerView = findViewById(R.id.rv);
        FlexboxLayoutManager flexBoxManager = new FlexboxLayoutManager(this);
        //按正常方向换行
        flexBoxManager.setFlexWrap(FlexWrap.WRAP);
        //主轴为水平方向，起点在左端
        flexBoxManager.setFlexDirection(FlexDirection.ROW);
        //定义项目在副轴轴上如何对齐
        flexBoxManager.setAlignItems(AlignItems.CENTER);
        //多个轴对齐方式（交叉轴的起点对齐）
        flexBoxManager.setJustifyContent(JustifyContent.FLEX_START);
        mRecyclerView.setLayoutManager(flexBoxManager);
        ArrayList<String> strings = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.main_txt)));
        FlexboxAdapter flexboxAdapter = new FlexboxAdapter(strings, R.layout.item_flow_text);
        mRecyclerView.setAdapter(flexboxAdapter);
        flexboxAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(Object data, int position) {
        switch (position) {
            case 0:
                //拍照上传
                PhotoUploadActivity.startAction(this);
                break;
            case 1:
                //沉浸式状态栏
                CJSActivity.startAction(this);
                break;
            case 2:
                //GreenDao使用
                startActivity(new Intent(this, GreenDaoActivity.class));
                break;
            case 3:
                // Arouter使用
                ArouterDemoActivity.startAction(this);
                break;
            case 4:
                StatusBarActivity.startAction(this);
                break;
            case 5:
//                KotlinDemoActivity.startAction(this);
                break;
        }
    }
}
