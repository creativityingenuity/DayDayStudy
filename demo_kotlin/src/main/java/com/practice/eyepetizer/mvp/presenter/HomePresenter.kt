package com.practice.eyepetizer.mvp.presenter

import com.practice.eyepetizer.mvp.base.BasePresenter
import com.practice.eyepetizer.mvp.contract.HomeContract
import com.practice.eyepetizer.mvp.model.HomeModel
import com.practice.eyepetizer.mvp.model.bean.HomeBean
import com.practice.eyepetizer.net.exception.ExceptionHandle

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
       val disposable =  homeModel.requestHomeData(num)
                .flatMap({homeBean-> // Lambda表达式总是被大括号括着  其参数(如果存在)在 -> 之前声明(参数类型可以省略)  函数体(如果存在)在 -> 后面。

                    var bannerItemList = homeBean.issueList[0].itemList

                    bannerItemList.filter {item ->
                        item.type == "banner2"|| item.type == "horizontalScrollCard"
                    }.forEach { item->
                        //移除item
                        bannerItemList.remove(item)
                    }
                    bannerHomeBean = homeBean

                    homeModel.loadMoreData(homeBean.nextPageUrl)
                })
               .subscribe({ homeBean ->
                   mRootView?.apply {
                       dismissLoading()

                       nextPageUrl = homeBean.nextPageUrl
                       //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                       val newBannerItemList = homeBean.issueList[0].itemList

                       newBannerItemList.filter { item ->
                           item.type == "banner2"||item.type=="horizontalScrollCard"
                       }.forEach { item->
                           //移除item
                           newBannerItemList.remove(item)
                       }

                       //重新赋值banner长度
                       bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size

                       //赋值过滤后的数据 + banner 数据
                       bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)

                       setHomeData(bannerHomeBean!!)
                   }
               }, {t ->
                   mRootView?.apply {
                       dismissLoading()
                       mRootView?.showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                   }
               })
    }

    override fun loadMoreData() {

    }

}