package com.practice.eyepetizer.mvp.presenter

import com.practice.eyepetizer.mvp.base.BasePresenter
import com.practice.eyepetizer.mvp.contract.SearchContract
import com.practice.eyepetizer.mvp.model.SearchModel
import com.practice.eyepetizer.net.exception.ExceptionHandle

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/30.
 */
class SearchPresenter: BasePresenter<SearchContract.View>(),SearchContract.Presenter{
    private var nextPageUrl: String? = null

    private val searchModel by lazy { SearchModel() }


    /**
     * 获取热门关键词
     */
    override fun requestHotWordData() {
        checkViewAttach()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(disposable = searchModel.requestHotWordData()
                .subscribe({ string ->
                    mRootView?.apply {
                        setHotWordData(string)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                }))
    }

    /**
     * 查询关键词
     */
    override fun querySearchData(words: String) {
        checkViewAttach()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(disposable = searchModel.getSearchResult(words)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        if (issue.count > 0 && issue.itemList.size > 0) {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        } else
                            setEmptyView()
                    }
                }, { throwable ->
                    mRootView?.apply {
                        dismissLoading()
                        //处理异常
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                })
        )

    }

    /**
     * 加载更多数据
     */
    override fun loadMoreData() {
        checkViewAttach()
        nextPageUrl?.let {
            addSubscription(disposable = searchModel.loadMoreData(it)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        }
                    }, { throwable ->
                        mRootView?.apply {
                            //处理异常
                            showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                        }
                    }))
        }

    }
}