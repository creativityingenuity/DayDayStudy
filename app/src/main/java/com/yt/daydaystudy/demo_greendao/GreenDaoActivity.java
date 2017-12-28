package com.yt.daydaystudy.demo_greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yt.daydaystudy.R;

/**
 * 目前使用较广，效率最高哈的数据库框架
 * 地址：https://github.com/greenrobot/greenDAO
 * 使用步骤：
 *      1.greenDao框架的初始化(准备工作与依赖)
 *      2.创建实体类 接着点击 Build -> Make Project
 */
public class GreenDaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
    }
}
