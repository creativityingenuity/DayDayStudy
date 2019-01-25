package com.practice.eyepetizer.mvp.model

import com.practice.eyepetizer.mvp.model.bean.HomeBean
import com.practice.eyepetizer.net.RetrofitManager
import com.practice.eyepetizer.net.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/25.
 */
class RankModel {
    /**
     * 获取排行
     */
    fun getRankList(url : String) : Observable<HomeBean.Issue>{
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }

}