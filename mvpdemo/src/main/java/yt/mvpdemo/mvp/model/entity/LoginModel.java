package yt.mvpdemo.mvp.model.entity;

import io.reactivex.Observable;
import yt.mvpdemo.base.BaseResponse;
import yt.mvpdemo.commen.RxManager;
import yt.mvpdemo.mvp.contract.LoginContract;
import yt.mvpdemo.net.NetHelper;

/**
 * Created by ${zhangyuanchao} on 2017/12/18.
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<BaseResponse<LoginEntity>> login(String name, String pwd) {
        return NetHelper.getApiServers()
                .login("48ecf289b4a3f977bb33e2d7eec91843","1512104887277","15555555555","123456")
                .compose(RxManager.<BaseResponse<LoginEntity>>rxSchedulerHelper());
    }
}
