package yt.mvpdemo.mvp.contract;

import yt.mvpdemo.base.BaseModel;
import yt.mvpdemo.base.BasePresenter;
import yt.mvpdemo.base.BaseView;

/**
 * Created by ${zhangyuanchao} on 2017/12/1.
 * 登录契约类
 */

public interface LoginContract {
    interface View extends BaseView{
        //登陆返回的数据
        void returnLogin(String name,String pwd);
    }

    interface Model extends BaseModel{

    }

    interface Presenter extends BasePresenter<View,Model> {
        //发起登陆请求
        void loginRequest(String name,String pwd);
    }
}
