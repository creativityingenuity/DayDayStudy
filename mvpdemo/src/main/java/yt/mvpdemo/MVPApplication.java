package yt.mvpdemo;

import android.app.Application;

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
    }

    public static Application getInstance(){
        return instance;
    }
}
