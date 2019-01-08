package com.yt.daydaystudy.demo_cjs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yt.daydaystudy.MainActivity;
import com.yt.daydaystudy.R;
import com.yt.daydaystudy.demo_recyclerview.DemoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 沉浸式状态栏
 */
public class CJSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cjs);
//        transparentAndCoverStatusBar(this);
        initView();
    }

    private void initView() {

        CollapsingToolbarLayout mCollapsTtoolbar = (CollapsingToolbarLayout) findViewById(R.id.collaps_toolbar);
        Toolbar  toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        final TextView mTitle = (TextView) findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mCollapsTtoolbar.setCollapsedTitleTextColor(Color.WHITE);
        mCollapsTtoolbar.setExpandedTitleColor(Color.WHITE);

        //设置标题渐隐渐现
        appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mTitle.setAlpha(-verticalOffset * 1.0f / appBarLayout.getTotalScrollRange());
            }
        });

        RecyclerView mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        for (int i = 0;i<500;i++){
            list.add("ceshi"+i);
        }
        mRv.setAdapter(new DemoAdapter(list));
    }

    public static void startAction(MainActivity mainActivity) {
        mainActivity.startActivity(new Intent(mainActivity,CJSActivity.class));
    }

    /**
     * 使状态栏透明,并覆盖状态栏，对API大于19的显示正常，但小于的界面扩充到状态栏，但状态栏不为透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void transparentAndCoverStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Resources.getSystem().getColor(android.R.color.background_dark));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
