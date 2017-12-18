package yt.mvpdemo.mvp.present;

import io.reactivex.observers.DisposableObserver;
import yt.mvpdemo.base.BaseResponse;
import yt.mvpdemo.mvp.contract.LoginContract;
import yt.mvpdemo.mvp.model.entity.LoginEntity;
import yt.mvpdemo.net.DefaultObserver;

/**
 * Created by ${zhangyuanchao} on 2017/12/1.
 */

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void loginRequest(String name, String pwd) {
        mRxManage.register(mModel.login(name,pwd)
                .subscribeWith(new DefaultObserver<BaseResponse<LoginEntity>>() {
                    @Override
                    protected void onSuccess(BaseResponse<LoginEntity> response) {
                       mView.returnLogin(response.getData());
                    }
                }));
    }
}
