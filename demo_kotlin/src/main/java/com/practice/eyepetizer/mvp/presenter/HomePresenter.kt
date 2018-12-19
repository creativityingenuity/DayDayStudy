package com.practice.eyepetizer.mvp.presenter

import android.content.Context
import com.practice.eyepetizer.mvp.contract.HomeContract
import com.practice.eyepetizer.mvp.model.HomeModel

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/12.
 */
class HomePresenter(context: Context,view : HomeContract.View) : HomeContract.Presenter{
    var mContext : Context? = null
    var mView : HomeContract.View? = null
    val mModel : HomeModel by lazy {
        HomeModel()
    }
    override fun start() {

    }

    override fun requestData() {

    }

}