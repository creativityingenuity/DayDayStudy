package com.yt.daydaystudy.demo_greendao;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yt.daydaystudy.R;
import com.yt.daydaystudy.demo_greendao.dao.Nan;
import com.yt.daydaystudy.demo_greendao.dao.Nv;
import com.yt.daydaystudy.demo_greendao.dao.User;

import java.util.ArrayList;
import java.util.List;


/**
 * 目前使用较广，效率最高哈的数据库框架
 * 地址：https://github.com/greenrobot/greenDAO
 * 使用步骤：
 * 1.greenDao框架的初始化(准备工作与依赖)
 * 2.创建实体类 接着点击 Build -> Make Project
 * 3.数据库操作类生成 开始用greenDao操作数据库:(用log来展示每次操作的数据变化):
 */
public class GreenDaoActivity extends AppCompatActivity {

    private ListView mLv;
    private List<String> list;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        setStatusBar();
        mLv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        mLv.setAdapter(arrayAdapter);
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    public void add(View v) {
        DBManager.getInstance().insertUser(new User(1, "杨拓", 23, "男"));
        DBManager.getInstance().insertUser(new User(2, "杨拓111", 23, "男"));
    }

    public void del(View v) {
//        DBManager.getInstance().deleteUser(new User(1, "杨拓", 23, "男"));
        DBManager.getInstance().getDaoSession().getNanDao().deleteAll();
        DBManager.getInstance().getDaoSession().getNvDao().deleteAll();
        //在此实现一对一 为啥不对  我也很郁闷
        Long wifeId1=new Long(100);
        Long wifeId2=new Long(200);
        Long wifeId3=new Long(300);

        Nan user1=new Nan(null,"a","1","man","11",wifeId1);
        Nan user2=new Nan(null,"b","2","man","22",wifeId2);
        Nan user3=new Nan(null,"c","3","man","33",wifeId3);
        DBManager.getInstance().getDaoSession().getNanDao().insert(user1);
        DBManager.getInstance().getDaoSession().getNanDao().insert(user2);
        DBManager.getInstance().getDaoSession().getNanDao().insert(user3);

        Nv wife1=new Nv(wifeId1,100,"A");
        Nv wife2=new Nv(wifeId2,200,"B");
        Nv wife3=new Nv(wifeId3,300,"C");
        DBManager.getInstance().getDaoSession().getNvDao().insert(wife1);
        DBManager.getInstance().getDaoSession().getNvDao().insert(wife2);
        DBManager.getInstance().getDaoSession().getNvDao().insert(wife3);

        List<Nan> nans = DBManager.getInstance().getDaoSession().getNanDao().queryBuilder().build().list();
        Log.d("MainActivity","size="+nans.size());
        String name1 = nans.get(0).getNv().getName();
        Log.d("MainActivity","name="+ name1);
        //还必须要走循环 在能在
        for (int i = 0; i < nans.size(); i++) {
            this.name += nans.get(i).getName() + "的wife是"+nans.get(i).getNv().getName();
        }

    }
    String name;
    public void update(View v) {
        DBManager.getInstance().updataUser(new User(2, "xxxxx", 23, "男"));
    }

    public void find(View v) {
        list.clear();
        List<User> users = DBManager.getInstance().queryUserList();
        for (User user : users) {
            list.add(user.getName());
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
