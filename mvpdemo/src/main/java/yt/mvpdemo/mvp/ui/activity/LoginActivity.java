package yt.mvpdemo.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yt.mvpdemo.R;
import yt.mvpdemo.base.BaseActivity;
import yt.mvpdemo.base.BaseResponse;
import yt.mvpdemo.mvp.contract.LoginContract;
import yt.mvpdemo.mvp.module.entity.LoginEntity;
import yt.mvpdemo.net.DefaultObserver;
import yt.mvpdemo.net.NetHelper;

public class LoginActivity extends BaseActivity implements LoginContract.View{

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
        /*登录*/
        String name = mEtName.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();

        NetHelper.getApiServers()
                .login("48ecf289b4a3f977bb33e2d7eec91843","1512104887277","15555555555","123456")
                .compose(this.<BaseResponse<LoginEntity>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<LoginEntity>>() {
                    @Override
                    protected void onSuccess(BaseResponse<LoginEntity> response) {

                    }
                });
    }
}
