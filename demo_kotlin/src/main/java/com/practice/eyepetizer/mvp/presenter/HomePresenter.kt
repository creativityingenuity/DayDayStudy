package com.practice.eyepetizer.mvp.presenter

import com.practice.eyepetizer.mvp.base.BasePresenter
import com.practice.eyepetizer.mvp.contract.HomeContract
import com.practice.eyepetizer.mvp.model.HomeModel
import com.practice.eyepetizer.mvp.model.bean.HomeBean

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/12.
 * 首页的presenter
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    /**
     * lazy 应用于单例模式，而且当且仅当变量被第一次调用的时候，委托方法才会执行。以后只会返回调用的结果
     * 比如这样的常见操作，只获取，不赋值，并且多次使用的对象
     */
    val homeModel: HomeModel by lazy {
        HomeModel()
    }
    private var bannerHomeBean: HomeBean? = null

    private var nextPageUrl:String?=null     //加载首页的Banner 数据+一页数据合并后，nextPageUrl没 add
    /**
     * 请求全部数据
     */
    override fun requestHomeData(num: Int) {
        //检测是否绑定view
        checkViewAttach()
        mRootView?.showLoading()
//        homeModel.requestHomeData(num)
//                ？
    }

    override fun loadMoreData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}