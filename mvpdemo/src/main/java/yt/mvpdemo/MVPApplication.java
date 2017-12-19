package yt.mvpdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import yt.myutils.Utils;

/**
 * Created by ${zhangyuanchao} on 2017/11/30.
 * 先写一个登陆案例
 */

public class MVPApplication extends Application {
    private static MVPApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static Application getInstance(){
        return instance;
    }
}
