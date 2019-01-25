package com.practice.eyepetizer.mvp.model

import com.practice.eyepetizer.mvp.model.bean.TabInfoBean
import com.practice.eyepetizer.net.RetrofitManager
import com.practice.eyepetizer.net.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/25.
 */
class HotModel {

    fun getTabInfo() : Observable<TabInfoBean>{
        return  RetrofitManager.service.getRankList()
                .compose(SchedulerUtils.ioToMain())
    }
}