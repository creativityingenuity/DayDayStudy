package yt.mvpdemo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.OnClick;
import yt.mvpdemo.R;
import yt.mvpdemo.base.BaseActivity;
import yt.mvpdemo.mvp.contract.LoginContract;
import yt.mvpdemo.mvp.model.entity.LoginEntity;
import yt.mvpdemo.mvp.model.LoginModel;
import yt.mvpdemo.mvp.present.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginModel,LoginPresenter> implements LoginContract.View{

    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.home_til_name)
    TextInputLayout mTilName;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.til_pwd)
    TextInputLayout mTilPwd;
    @Bind(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String name = mEtName.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        //登录
        mPresenter.loginRequest(name,pwd);
    }

    @Override
    public void returnLogin(LoginEntity loginEntity) {
        startActivity(new Intent(this,MainActivity.class));
        System.out.println(loginEntity.getName());
    }
}
