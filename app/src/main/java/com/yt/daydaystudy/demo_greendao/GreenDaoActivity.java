package com.yt.daydaystudy.demo_greendao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yt.daydaystudy.MyApplication;
import com.yt.daydaystudy.R;
import com.yt.daydaystudy.demo_greendao.dao.User;
import com.yt.daydaystudy.greendao.UserDao;

import java.util.List;

/**
 * 目前使用较广，效率最高哈的数据库框架
 * 地址：https://github.com/greenrobot/greenDAO
 * 使用步骤：
 *      1.greenDao框架的初始化(准备工作与依赖)
 *      2.创建实体类 接着点击 Build -> Make Project
 *      3.数据库操作类生成 开始用greenDao操作数据库:(用log来展示每次操作的数据变化):
 */
public class GreenDaoActivity extends AppCompatActivity {

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
         userDao = MyApplication.getInstance().getDaoSession().getUserDao();
    }

    public void add(View v){
        User user = new User(2,"yt",32,"男");
        userDao.insert(user);
    }

    public void del(View v){
        userDao.delete();
    }

    public void update(View v){
        userDao.update();
    }

    public void find(View v){
        List<User> users = mUserDao.loadAll();
        String userName = "";
        for (int i = 0; i < users.size(); i++) {
            userName += users.get(i).getName()+",";
        }
        mContext.setText("查询全部数据==>"+userName);
    }
}
