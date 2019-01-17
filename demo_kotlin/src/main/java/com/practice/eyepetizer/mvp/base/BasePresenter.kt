package com.practice.eyepetizer.mvp.base


interface BasePresenter<in V : BaseView> {
    fun attachView(mRootView: V)

    fun detachView()
}