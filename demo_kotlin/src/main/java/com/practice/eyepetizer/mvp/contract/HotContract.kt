package com.practice.eyepetizer.mvp.contract

import com.practice.eyepetizer.mvp.base.IBaseView
import com.practice.eyepetizer.mvp.base.IPresenter
import com.practice.eyepetizer.mvp.model.bean.TabInfoBean

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/25.
 */
interface HotContract {
    interface View : IBaseView{
        /**
         * 设置 TabInfo
         */
        fun setTabInfo(tabInfoBean: TabInfoBean)

        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter : IPresenter<View>{
        /**
         * 获取 TabInfo
         */
        fun getTabInfo()
    }
}