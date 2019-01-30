package com.hazz.kotlinmvp.mvp.presenter

import com.hazz.kotlinmvp.mvp.model.CategoryModel
import com.practice.eyepetizer.mvp.base.BasePresenter
import com.practice.eyepetizer.mvp.contract.CategoryContract
import com.practice.eyepetizer.net.exception.ExceptionHandle


/**
 * Created by xuhao on 2017/11/29.
 * desc:分类的 Presenter
 */
class CategoryPresenter : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    private val categoryModel: CategoryModel by lazy {
        CategoryModel()
    }

    /**
     * 获取分类
     */
    override fun getCategoryData() {
        checkViewAttach()
        mRootView?.showLoading()
        val disposable = categoryModel.getCategoryData()
                .subscribe({ categoryList ->
                    mRootView?.apply {
                        dismissLoading()
                        showCategory(categoryList)
                    }
                }, { t ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                    }

                })

        addSubscription(disposable)
    }
}