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
        //Observable<BaseResponse<LoginEntity>>
        mRxManage.register(mModel.login(name,pwd).subscribeWith(new DisposableObserver<BaseResponse<LoginEntity>>() {
            @Override
            public void onNext(BaseResponse<LoginEntity> loginEntityBaseResponse) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }));
        mModel.login(name, pwd)
                .subscribe(new DefaultObserver<BaseResponse<LoginEntity>>(mRxManage) {
                    @Override
                    protected void onSuccess(BaseResponse<LoginEntity> response) {
                        LoginEntity data = response.getData();
                        System.out.println(data.getName() + "xxxxxxxxxxxxxxxxx");
                    }
                });

//        new Consumer<BaseResponse<LoginEntity>>() {
//            @Override
//            public void accept(@NonNull BaseResponse<LoginEntity> loginEntityBaseResponse) throws Exception {
//                LoginEntity data = loginEntityBaseResponse.getData();
//                System.out.println(data.getName() + "xxxxxxxxxxxxxxxxx");
//            }
//        })
    }
}
