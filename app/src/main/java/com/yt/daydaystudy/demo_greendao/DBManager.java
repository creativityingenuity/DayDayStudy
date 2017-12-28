package com.yt.daydaystudy.demo_greendao;

import android.database.sqlite.SQLiteDatabase;

import com.yt.daydaystudy.MyApplication;
import com.yt.daydaystudy.demo_greendao.dao.User;
import com.yt.daydaystudy.demo_greendao.greendao.DaoMaster;
import com.yt.daydaystudy.demo_greendao.greendao.DaoSession;
import com.yt.daydaystudy.demo_greendao.greendao.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 数据库管理
 */

public class DBManager {
    private DaoSession mDaoSession;
    private String dbName = "test_db";
    private DaoMaster.DevOpenHelper mHelper;

    private DBManager() {
        initGreenDao();
    }

    private static DBManager mInstance;

    public static DBManager getInstance() {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager();
                }
            }
        }
        return mInstance;
    }

    private void initGreenDao() {
        mHelper = new DaoMaster.DevOpenHelper(MyApplication.getInstance().getApplicationContext(), dbName, null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 插入一条数据
     *
     * @param user
     */
    public void insertUser(User user) {
        mDaoSession.getUserDao().insert(user);
    }

    /**
     * 插入用户集合
     *
     * @param userList
     */
    public void insertUserList(List<User> userList) {
        if (userList == null || userList.isEmpty()) return;
        mDaoSession.getUserDao().insertInTx(userList);
    }

    /**
     * 删除一条用户记录
     *
     * @param user
     */
    public void deleteUser(User user) {
        mDaoSession.getUserDao().delete(user);
    }

    /**
     * '
     * 更新用户数据
     *
     * @param user
     */
    public void updataUser(User user) {
        mDaoSession.update(user);
    }

    /**
     * 查询用户列表
     * @return
     */
    public List<User> queryUserList() {
        QueryBuilder<User> userQueryBuilder = mDaoSession.getUserDao().queryBuilder();
        return userQueryBuilder.list();
    }

    /**
     * 查询用户列表
     */
    public List<User> queryUserList(int age) {
        QueryBuilder<User> userQueryBuilder = mDaoSession.getUserDao().queryBuilder().where(UserDao.Properties.Age.gt(age)).orderAsc(UserDao.Properties.Age);
        return userQueryBuilder.list();
    }
}
