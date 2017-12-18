package yt.mvpdemo.mvp.contract;

import io.reactivex.Observable;
import yt.mvpdemo.base.BaseModel;
import yt.mvpdemo.base.BasePresenter;
import yt.mvpdemo.base.BaseResponse;
import yt.mvpdemo.base.BaseView;
import yt.mvpdemo.mvp.model.entity.LoginEntity;

/**
 * Created by ${zhangyuanchao} on 2017/12/1.
 * 登录契约类
 */

public interface LoginContract {
    interface View extends BaseView{
        //登陆请求返回的数据
        void returnLogin(LoginEntity loginEntity);
    }

    interface Model extends BaseModel{
        Observable<BaseResponse<LoginEntity>> login(String name, String pwd);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        //发起登陆请求
       protected abstract void loginRequest(String name, String pwd);
    }
}
