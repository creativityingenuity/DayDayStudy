package com.practice.eyepetizer.mvp.contract

import com.practice.eyepetizer.mvp.base.IBaseView
import com.practice.eyepetizer.mvp.base.IPresenter
import com.practice.eyepetizer.mvp.model.bean.HomeBean

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/25.
 */
interface RankContract {
    interface  View : IBaseView{
        /**
         * 设置排行榜的数据
         */
        fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter : IPresenter<View>{
        /**
         * 获取 TabInfo
         */
        fun requestRankList(apiUrl:String)
    }
}