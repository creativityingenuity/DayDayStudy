package com.yt.daydaystudy;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import yt.myutils.Utils;
import yt.myutils.core.AppUtils;



public class MyApplication extends Application {
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
        if (AppUtils.isAppDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        //初始化Arouter
        ARouter.init(this);
    }

    public static MyApplication getInstance(){
        return instance;
    }

}
