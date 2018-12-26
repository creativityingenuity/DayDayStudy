package com.practice.eyepetizer.mvp.presenter

import android.content.Context
import com.practice.eyepetizer.mvp.contract.HomeContract
import com.practice.eyepetizer.mvp.model.HomeModel
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import io.reactivex.Observable

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/12.
 */
class HomePresenter(context: Context, view: HomeContract.View) : HomeContract.Presenter {
    var mContext: Context? = null
    var mView: HomeContract.View? = null
    val mModel: HomeModel by lazy {
        HomeModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
        requestData()
    }

    override fun requestData() {
        val observable : Observable<HomeBean>? = mContext?.let { mModel.loadData(it,true,"0") }
    }

    fun moreData(data : String){

    }
}