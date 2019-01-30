package com.practice.eyepetizer.mvp.model

import com.practice.eyepetizer.mvp.model.bean.HomeBean
import com.practice.eyepetizer.net.RetrofitManager
import com.practice.eyepetizer.net.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/30.
 */
class SearchModel {
    /**
     * 请求热门关键词的数据
     */
    fun requestHotWordData(): Observable<ArrayList<String>> {

        return RetrofitManager.service.getHotWord()
                .compose(SchedulerUtils.ioToMain())
    }


    /**
     * 搜索关键词返回的结果
     */
    fun getSearchResult(words: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getSearchData(words)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }
}