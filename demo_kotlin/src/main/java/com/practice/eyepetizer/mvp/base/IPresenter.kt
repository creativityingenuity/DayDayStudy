package com.practice.eyepetizer.mvp.base

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/18.
 */
interface IPresenter<in V: IBaseView> {
    fun attachView(mRootView: V)

    fun detachView()
}