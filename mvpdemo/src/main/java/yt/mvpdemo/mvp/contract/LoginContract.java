package yt.mvpdemo.mvp.contract;

import yt.mvpdemo.base.BaseModel;
import yt.mvpdemo.base.BasePresent;
import yt.mvpdemo.base.BaseView;

/**
 * Created by ${zhangyuanchao} on 2017/12/1.
 * 登录契约类
 */

public interface LoginContract {
    interface LoginView extends BaseView{

    }

    interface LoginPresent extends BasePresent{
        /*登录*/
        void login(String name, String pwd);
    }

    interface LoginModule extends BaseModel{

    }
}
