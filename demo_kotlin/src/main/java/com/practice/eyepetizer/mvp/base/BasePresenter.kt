package com.practice.eyepetizer.mvp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class BasePresenter<T : IBaseView> : IPresenter<T> {
    var mRootView: T? = null
        private set
    private var compositeDisposable = CompositeDisposable()

    private val isViewAttached: Boolean
        get() = mRootView != null

    /**
     * 关联view
     */
    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    /**
     * 解除关联
     */
    override fun detachView() {
        mRootView = null
        //在activity结束时取消正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    fun checkViewAttach(){
        if(!isViewAttached) throw MvpViewNotAttachedException()
    }
    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")

}