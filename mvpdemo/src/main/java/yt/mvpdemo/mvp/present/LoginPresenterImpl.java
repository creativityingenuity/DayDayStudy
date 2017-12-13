package yt.mvpdemo.mvp.present;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yt.mvpdemo.base.BaseResponse;
import yt.mvpdemo.mvp.contract.LoginContract;
import yt.mvpdemo.mvp.module.entity.LoginEntity;
import yt.mvpdemo.mvp.ui.activity.LoginActivity;
import yt.mvpdemo.net.DefaultObserver;
import yt.mvpdemo.net.NetHelper;

/**
 * Created by ${zhangyuanchao} on 2017/12/1.
 */

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.View mView;
    private LoginContract.Model mModel;
    LoginActivity loginActivity;
    public LoginPresenterImpl(LoginActivity loginActivity) {
//        mModel = new
        this.loginActivity = loginActivity;
    }

    @Override
    public void loginRequest(String name, String pwd) {
        NetHelper.getApiServers()
                .login("48ecf289b4a3f977bb33e2d7eec91843","1512104887277","15555555555","123456")
                .compose(loginActivity.<BaseResponse<LoginEntity>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<LoginEntity>>() {
                    @Override
                    protected void onSuccess(BaseResponse<LoginEntity> response) {
                        LoginEntity data = response.getData();
                        System.out.println(data.getName()+"xxxxxxxxxxxxxxxxx");
                    }
                });
    }
}
