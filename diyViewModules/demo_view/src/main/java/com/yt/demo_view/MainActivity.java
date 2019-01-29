package com.yt.demo_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.yt.demo_view.bannerview.BannerActivity;
import com.yt.demo_view.changeskin.ChangeSkinActivity;
import com.yt.demo_view.listview.ListViewPracticeActivity;
import com.yt.demo_view.lockview.LockScreenActivity;
import com.yt.demo_view.treeview.zj.TreeViewActivity1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 项目中以及闲暇写的UI效果
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        ArrayList<String> strings = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.fl)));
        FlexboxAdapter flexboxAdapter = new FlexboxAdapter(strings, R.layout.item_flow_text);
        mRecyclerView.setAdapter(flexboxAdapter);
        flexboxAdapter.setOnItemClickListener(mClickListener);
    }

    private CommonAdapter.OnItemClickListener mClickListener = new CommonAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Object data, int position) {
            switch (position){
                case 0:
                    LockScreenActivity.startAction(MainActivity.this);
                    break;
                case 1:
                    TreeViewActivity1.startAction(MainActivity.this);
                    break;
                case 2:
                    BannerActivity.startAction(MainActivity.this);
                    break;
                case 3:
                    ChangeSkinActivity.startAction(MainActivity.this);
                    break;
                case 4:
                    ListViewPracticeActivity.startAction(MainActivity.this);
                    break;
                case 5:
                    LockScreenActivity.startAction(MainActivity.this);
                    break;
            }
        }
    };

}
