package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;

import com.blmf.R;
import com.blmf.mvp.ui.fragment.login.ForgetpwdFragment1;
import com.blmf.mvp.ui.fragment.login.ForgetpwdFragment2;

import blmf.lib_commen.base.BaseActivity;
import blmf.lib_commen.utils.FragmentUtils;

/**
 * Created by ${zhangyuanchao} on 2017/12/14.
 * 忘记密码界面
 */

public class ForgetActivity extends BaseActivity implements ForgetpwdFragment1.onForgetFragment1ItemClick ,ForgetpwdFragment2.onForgetFragment2ItemClick {

    private ForgetpwdFragment1 forgetpwdFragment1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mywallet;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        forgetpwdFragment1 = ForgetpwdFragment1.newInstance();
        FragmentUtils.add(getSupportFragmentManager(), forgetpwdFragment1,R.id.container);
    }

    /**
     * 下一步按钮
     */
    @Override
    public void onNextItemCLickListener(String phone) {
        ForgetpwdFragment2 forgetpwdFragment2 = ForgetpwdFragment2.newInstance(phone);
//        FragmentUtils.hide(forgetpwdFragment1);
        FragmentUtils.add(getSupportFragmentManager(), forgetpwdFragment2,R.id.container,false,true);
        FragmentUtils.showHide(forgetpwdFragment2,forgetpwdFragment1);
    }

    @Override
    public void onSubmitItemCLickListener() {
        finish();
    }

    @Override
    public void onBackItemCLickListener() {
        finish();
    }
}
