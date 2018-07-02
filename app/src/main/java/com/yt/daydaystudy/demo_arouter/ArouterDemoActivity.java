package com.yt.daydaystudy.demo_arouter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yt.daydaystudy.R;
import com.yt.daydaystudy.demo_greendao.dao.User;

/**
 //route path 注解
 * 在支持路由的页面添加注解
 * 这里的路径至少需要两级/xx/xx  注意
 * 路径标签建议写在一个类中  方便统一管理
 *
 */
//1.注解
@Route(path = RoutePathContract.ROUTEPATH_AROUTERDEMOACTIVITY)
public class ArouterDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter_demo2);
        //2.进行注入
        ARouter.getInstance().inject(this);
        //3.跳转simpleActivity
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePathContract.ROUTEPATH_SIMPLEACTIVITY)
                        .withString("name","哇哈哈")
                        .withParcelable("pa",new User(1,"ada",2,"nan"))

//                        .withTransition()  页面跳转动画
                        .navigation();
            }
        });
    }

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity,ArouterDemoActivity.class);
        activity.startActivity(intent);
    }
}
