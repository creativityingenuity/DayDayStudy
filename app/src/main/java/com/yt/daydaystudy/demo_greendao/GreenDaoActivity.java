package com.yt.daydaystudy.demo_greendao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yt.daydaystudy.R;
import com.yt.daydaystudy.demo_greendao.dao.User;

import java.util.ArrayList;
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

    private ListView mLv;
    private List<String> list;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        mLv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        mLv.setAdapter(arrayAdapter);
    }

    public void add(View v){
        DBManager.getInstance().insertUser(new User(1,"杨拓",23,"男"));
        DBManager.getInstance().insertUser(new User(2,"杨拓111",23,"男"));
    }

    public void del(View v){
        DBManager.getInstance().deleteUser(new User(1,"杨拓",23,"男"));
    }

    public void update(View v){
        DBManager.getInstance().updataUser(new User(2,"xxxxx",23,"男"));
    }

    public void find(View v){
        list.clear();
        List<User> users = DBManager.getInstance().queryUserList();
        for (User user : users) {
            list.add(user.getName());
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
