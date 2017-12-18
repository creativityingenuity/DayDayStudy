package yt.mvpdemo.mvp.present;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yt.mvpdemo.base.BaseResponse;
import yt.mvpdemo.mvp.contract.LoginContract;
import yt.mvpdemo.mvp.model.entity.LoginEntity;
import yt.mvpdemo.mvp.ui.activity.LoginActivity;
import yt.mvpdemo.net.DefaultObserver;
import yt.mvpdemo.net.NetHelper;

/**
 * Created by ${zhangyuanchao} on 2017/12/1.
 */

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void loginRequest(String name, String pwd) {
        mRxManage.register(mModel.login(name, pwd)
                .subscribe(new DefaultObserver<BaseResponse<LoginEntity>>() {
                    @Override
                    protected void onSuccess(BaseResponse<LoginEntity> response) {
//                        mView.returnLogin(response.getData());
                        LoginEntity data = response.getData();
                        System.out.println(data.getName() + "xxxxxxxxxxxxxxxxx");
                    }
                }));
    }
}
