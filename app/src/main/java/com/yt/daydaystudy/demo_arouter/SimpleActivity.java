package com.yt.daydaystudy.demo_arouter;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.yt.daydaystudy.R;
import com.yt.daydaystudy.demo_greendao.dao.User;

import yt.myutils.core.LogUtils;

@Route(path = RoutePathContract.ROUTEPATH_SIMPLEACTIVITY)
public class SimpleActivity extends BaseActivity {
    @Autowired
    String name;
    @Autowired(name = "pa")
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        LogUtils.e(name);
        LogUtils.e(user.toString());
    }
}
