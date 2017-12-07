package com.yt.daydaystudy;

import android.app.Application;

import yt.myutils.Utils;

/**
 * Created by ${zhangyuanchao} on 2017/12/7.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
