package com.practice.eyepetizer.mvp.contract

import com.practice.eyepetizer.mvp.base.BasePresenter
import com.practice.eyepetizer.mvp.base.BaseView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean


/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/17.
 */
interface HomeContract {
    interface Presenter : BasePresenter {
        fun requestData()
    }
    interface View : BaseView<Presenter> {
        fun setData(bean : HomeBean)
    }
}