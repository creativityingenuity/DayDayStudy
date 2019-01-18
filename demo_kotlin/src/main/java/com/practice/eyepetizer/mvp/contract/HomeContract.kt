package com.practice.eyepetizer.mvp.contract

import com.practice.eyepetizer.mvp.base.IBaseView
import com.practice.eyepetizer.mvp.base.IPresenter


/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/17.
 */
interface HomeContract {
    interface Presenter : IPresenter<View> {
        /**
         * 获取首页精选数据
         */
        fun requestHomeData(num: Int)

        /**
         * 加载更多数据
         */
        fun loadMoreData()
    }
    interface View : IBaseView {
        /**
         * 设置第一次请求的数据
         */
        fun setHomeData(homeBean: HomeBean)
    }
}