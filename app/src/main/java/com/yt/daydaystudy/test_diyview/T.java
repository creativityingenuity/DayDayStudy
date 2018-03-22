package com.yt.daydaystudy.test_diyview;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/3/7.
 */

public class T {
    private T (){}
    private static T instance;
    public static T getInstance(){
        if(instance==null){
            synchronized (T.class){
                if(instance==null){
                    instance = new T();
                }
            }
        }
        return instance;
    }
}
