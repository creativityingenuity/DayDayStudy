package com.yt.demo_view.treeview.zj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.yt.demo_view.R;

import java.util.ArrayList;
import java.util.List;

public class TreeViewActivity1 extends AppCompatActivity {
    private List<FileBean> mDatas = new ArrayList<FileBean>();
    private ListView mTree;
    private TreeListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_view1);
        initDatas();
        mTree = (ListView) findViewById(R.id.id_tree);
        try {

            mAdapter = new SimpleTreeAdapter<FileBean>(mTree, this, mDatas, 10);
            mTree.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void initDatas() {

        // id , pid , label , 其他属性
        mDatas.add(new FileBean(1, 0, "文件管理系统"));
        mDatas.add(new FileBean(2, 1, "游戏"));
        mDatas.add(new FileBean(3, 1, "文档"));
        mDatas.add(new FileBean(4, 1, "程序"));
        mDatas.add(new FileBean(5, 2, "war3"));
        mDatas.add(new FileBean(6, 2, "刀塔传奇"));

        mDatas.add(new FileBean(7, 4, "面向对象"));
        mDatas.add(new FileBean(8, 4, "非面向对象"));

        mDatas.add(new FileBean(9, 7, "C++"));
        mDatas.add(new FileBean(10, 7, "JAVA"));
        mDatas.add(new FileBean(11, 7, "Javascript"));
        mDatas.add(new FileBean(12, 8, "C"));

    }


}
