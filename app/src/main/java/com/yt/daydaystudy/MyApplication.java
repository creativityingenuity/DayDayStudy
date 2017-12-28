package com.yt.daydaystudy;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.yt.daydaystudy.greendao.DaoMaster;
import com.yt.daydaystudy.greendao.DaoSession;

import yt.myutils.Utils;

/**
 * Created by ${zhangyuanchao} on 2017/12/7.
 */

public class MyApplication extends Application {

    private DaoSession mDaoSession;
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
        initGreenDao();
    }

    public static MyApplication getInstance(){
        return instance;
    }
    /**
     *
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
