package com.example.demo_oneactivity_multifragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * 作为流程对外暴露的接口，对外数据交互（startActivityForResult 和 onActivityResult）
 * 负责流程步骤的调度，决定步骤间调用的先后顺序
 * 流程步骤间数据共享的通道
 */
public class LoginActivity extends AppCompatActivity {

    private Fragment1 fragment1;//设置手机号界面
    private Fragment2 fragment2;//设置名称界面
    private Fragment3 fragment3;//设置密码界面
    public String phone;
    public String name;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //保证activity无论在首次启动还是销毁重建的情况下都能获取正确的fragment
        fragment1 = loadFragment(Fragment1.class);
        fragment2 = loadFragment(Fragment2.class);
        fragment3 = loadFragment(Fragment3.class);
        // 如果是首次启动，把流程的第一个步骤代表的 Fragment 压栈
        if (savedInstanceState == null) {
            push(fragment1);
        }
        //拿到手机号后启动设置名称界面
        fragment1.setOnFragment1ItemClickCallBack(new Fragment1.OnFragment1ItemClickListener() {
            @Override
            public void onPhoneSetOk(String phone) {
                LoginActivity.this.phone = phone;
                push(fragment2);
            }
        });

        //拿到名称设置密码
        fragment2.setOnFragment2ItemClickCallBack(new Fragment2.OnFragment2ItemClickListener() {
            @Override
            public void onNameSetOk(String name) {
                LoginActivity.this.name = name;
                fragment3.setParams(phone,name);
                push(fragment3);
            }
            //test
            @Override
            public void onBackItemCLickListener() {

            }
        });
        //设置完密码，此流程结束
        fragment3.setOnFragment3ItemClickCallBack(new Fragment3.OnFragment3ItemClickListener() {
            @Override
            public void onPwdSetOk(User user) {
                LoginActivity.this.user = user;
                Intent intent = new Intent();
                intent.putExtra("user", user);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    /**
     * 加载fragment
     * @param fragmentclass
     * @param <T>
     * @return
     */
    private <T extends Fragment>T loadFragment(Class<T> fragmentclass) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        T fragment = (T) fragmentManager.findFragmentByTag(fragmentclass.getSimpleName());
        if(null==fragment){
            try {
                fragment = fragmentclass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }

    /**
     * 添加fragment的方法
     *
     * @param fragment
     */
    protected void push(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //第一个打开的fragment不需要动画
        if (fragments.size() != 0) {
            transaction.setCustomAnimations(R.anim.push_in_left, R.anim.push_out_left, R.anim.push_in_right, R.anim.push_out_right);
        }
        if (!fragment.isAdded())
            transaction.add(R.id.fl_container, fragment, fragment.getClass().getSimpleName());
        //第二个fragment进入，需要隐藏上一个fragment
        if (fragments.size() != 0) {
            transaction.hide(fragments.get(fragments.size() - 1))
                    .addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }
}
