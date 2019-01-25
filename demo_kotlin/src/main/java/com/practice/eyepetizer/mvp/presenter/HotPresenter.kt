package com.practice.eyepetizer.mvp.presenter

import com.practice.eyepetizer.mvp.base.BasePresenter
import com.practice.eyepetizer.mvp.contract.HotContract
import com.practice.eyepetizer.mvp.model.HotModel

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/25.
 */
class HotPresenter : BasePresenter<HotContract.View>(),HotContract.Presenter {

    private val hotModel by lazy {  HotModel() }

    override fun getTabInfo() {
        checkViewAttach()
        hotModel.getTabInfo()
                .subscribe({

                })
    }
}