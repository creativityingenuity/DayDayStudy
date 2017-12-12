package yt.mvpdemo.mvp.present;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yt.mvpdemo.base.BaseResponse;
import yt.mvpdemo.mvp.contract.LoginContract;
import yt.mvpdemo.mvp.module.entity.LoginEntity;
import yt.mvpdemo.net.DefaultObserver;
import yt.mvpdemo.net.RetrofitHelper;

/**
 * Created by ${zhangyuanchao} on 2017/12/1.
 */

public class LoginPresentImpl implements LoginContract.LoginPresent {
    @Override
    public void login(String name, String pwd) {
        RetrofitHelper.getApiServers()
                .login("48ecf289b4a3f977bb33e2d7eec91843","1512104887277","15555555555","123456")
                .subscribeOn(Schedulers.io())
//                .compose(RxLifecycle.bindUntilEvent(lifecycle, ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResponse<LoginEntity>>() {
                    @Override
                    protected void onSuccess(BaseResponse response) {

                    }
                });
    }
}
