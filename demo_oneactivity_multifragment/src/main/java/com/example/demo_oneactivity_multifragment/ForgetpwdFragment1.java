package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blmf.R;
import com.blmf.mvp.contract.ChangePhoneContract;
import com.blmf.mvp.model.ChangePhoneModel;
import com.blmf.mvp.model.entity.RegisterEntity;
import com.blmf.mvp.presenter.ChangePhonePresenter;
import com.blmf.mvp.ui.activity.ForgetActivity;

import blmf.lib_commen.base.BaseFragment;
import blmf.lib_commen.utils.CountDownTimerUtils;
import blmf.lib_commen.utils.ToastUtils;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ${zhangyuanchao} on 2017/12/18.
 */

public class ForgetpwdFragment1 extends BaseFragment<ChangePhonePresenter,ChangePhoneModel> implements ChangePhoneContract.View{
    @Bind(R.id.iv_top_left)
    ImageView ivTopLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_phone)
    EditText tvPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.btn_sendcode)
    Button btnSendcode;
    @Bind(R.id.btn_next)
    Button btnNext;
    private CountDownTimerUtils timerUtils;
    public static ForgetpwdFragment1 newInstance() {
        Bundle args = new Bundle();
        ForgetpwdFragment1 fragment = new ForgetpwdFragment1();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd_one;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle.setText("找回登录密码");
        ivTopLeft.setVisibility(View.VISIBLE);
        timerUtils = new CountDownTimerUtils(mActivity,btnSendcode);
    }

    @OnClick({R.id.iv_top_left, R.id.btn_sendcode, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_top_left:
                mActivity.finish();
                break;
            case R.id.btn_sendcode:
                String s = tvPhone.getText().toString();
                if(TextUtils.isEmpty(s)){
                    showToast("请输入手机号");
                    return;
                }
                mPresenter.sendCodeRequest(s,"");
                timerUtils.start();
                break;
            case R.id.btn_next:
                String code = etCode.getText().toString().trim();
                if(TextUtils.isEmpty(code)){
                    ToastUtils.showShort("请输入验证码");
                }else {
                    mPresenter.verifyCodeRequest(tvPhone.getText().toString(),code,"3");
                }
                break;
        }
    }

    @Override
    public void returnsendCode(String msg) {
        showToast(msg);
    }

    @Override
    public void returnVetifyCode(String msg) {
        ToastUtils.showShort(msg);
        if(mActivity instanceof onForgetFragment1ItemClick){
            ((ForgetActivity) mActivity).onNextItemCLickListener(tvPhone.getText().toString());
        }
    }
    //unuse
    @Override
    public void returnRegister(RegisterEntity data) {

    }

    public interface onForgetFragment1ItemClick{
        void onNextItemCLickListener(String phone);
    }
}
