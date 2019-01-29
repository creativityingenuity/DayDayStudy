package com.yt.demo_view.listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.yt.demo_view.MainActivity;
import com.yt.demo_view.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewPracticeActivity extends AppCompatActivity {

    private List<AppInfo> userApp = new ArrayList<>();
    private List<AppInfo> systemApp = new ArrayList<>();
    private SoftWareAdapter myAdapter;
    private TextView tv_title;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_practice);
        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_softwarem_title);
        lv = (ListView) findViewById(R.id.lv_softwarem);
        initData();
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                final List<AppInfo> appInfos = AppInfoTools.getAppInfo(ListViewPracticeActivity.this);
                for (AppInfo appInfo : appInfos) {
                    //判断应用程序信息，将应用程序存放到不同的集合中
                    if (appInfo.isSystem) {
                        systemApp.add(appInfo);
                    } else {
                        userApp.add(appInfo);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_title.setText("用户程序(" + userApp.size() + ")");
                        myAdapter = new SoftWareAdapter(ListViewPracticeActivity.this, systemApp, userApp);
                        lv.setAdapter(myAdapter);
                    }
                });
            }
        }.start();

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            /**
             * 滚动时回调的方法
             * @param view
             * @param firstVisibleItem 当前区域内可视的第一个item
             * @param visibleItemCount 当前区域内可视的item总数
             * @param totalItemCount item总数
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (userApp != null && systemApp != null) {
                    if (firstVisibleItem >= userApp.size() + 1) {
                        tv_title.setText("系统程序(" + systemApp.size() + ")");
                    } else {
                        tv_title.setText("用户程序(" + userApp.size() + ")");
                    }
                }
            }
        });
    }

    public static void startAction(MainActivity activity) {
        activity.startActivity(new Intent(activity,ListViewPracticeActivity.class));
    }
}
