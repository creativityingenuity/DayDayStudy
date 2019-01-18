package com.practice.eyepetizer.mvp.model

import com.practice.eyepetizer.mvp.model.bean.HomeBean
import com.practice.eyepetizer.net.RetrofitManager
import com.practice.eyepetizer.net.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/17.
 */
class HomeModel {
    fun requestHomeData(num: Int): Observable<HomeBean> {
        return RetrofitManager.service.getFirstHomeData(num)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url:String):Observable<HomeBean>{

        return RetrofitManager.service.getMoreHomeData(url)
                .compose(SchedulerUtils.ioToMain())
    }
}