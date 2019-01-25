package com.practice.eyepetizer.mvp.presenter

import com.practice.eyepetizer.mvp.base.BasePresenter
import com.practice.eyepetizer.mvp.contract.RankContract
import com.practice.eyepetizer.mvp.model.RankModel
import com.practice.eyepetizer.net.exception.ExceptionHandle

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/25.
 */
class RankPresenter : BasePresenter<RankContract.View>(), RankContract.Presenter {
    private val rankModel by lazy { RankModel() }

    /**
     * 请求排行数据
     */
    override fun requestRankList(apiUrl: String) {
        checkViewAttach()
        mRootView?.showLoading()
        var disposable = rankModel.getRankList(apiUrl)
                .subscribe({
                    mRootView?.dismissLoading()
                    mRootView?.setRankList(it.itemList)
                }, {
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                })
        addSubscription(disposable)
    }
}