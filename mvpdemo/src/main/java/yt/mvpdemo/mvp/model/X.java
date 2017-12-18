package yt.mvpdemo.mvp.model;

import io.reactivex.subscribers.DisposableSubscriber;
import yt.mvpdemo.base.BaseResponse;

/**
 * Created by ${zhangyuanchao} on 2017/12/18.
 */

public class X<T extends BaseResponse> extends DisposableSubscriber<T>  {


    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
