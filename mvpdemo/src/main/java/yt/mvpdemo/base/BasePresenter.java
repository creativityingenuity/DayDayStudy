package yt.mvpdemo.base;

import android.content.Context;

import yt.mvpdemo.commen.RxManager;

/**
 * Created by ${zhangyuanchao} on 2017/11/30.
 */

public abstract class BasePresenter<V, M> {
    public Context mContext;
    public M mModel;
    public V mView;
    //Rxjava+{组件 生命周期处理
    public RxManager mRxManage = new RxManager();

    /**
     * 关联vm
     *
     * @param v
     * @param m
     */
    public void attachVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
    }

    public void onDetachVM() {
        mRxManage.unSubscribe();
        mModel = null;
        mView = null;
    }
}
