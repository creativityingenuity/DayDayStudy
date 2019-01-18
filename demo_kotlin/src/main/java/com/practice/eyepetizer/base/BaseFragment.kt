package com.tt.lvruheng.eyepetizer.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.classic.common.MultipleStatusView


abstract class BaseFragment : Fragment() {
    /**
     * 视图是否加载完毕
     */
    var isViewInitiated: Boolean = false
    /**
     * 数据是否加载过了
     */
    var isDataLoaded: Boolean = false
    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null
    var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (rootView == null) {
            rootView = inflater?.inflate(getLayoutId(), container, false)
        }
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewInitiated = true
        init(savedInstanceState)
        prepareFetchData()
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        prepareFetchData()
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        loadData()
    }

    private fun prepareFetchData(): Boolean {
        return prepareFetchData(false)
    }

    private fun prepareFetchData(forceUpdate: Boolean): Boolean {
        if (userVisibleHint && isViewInitiated && (!isDataLoaded || forceUpdate)) {
            loadData()
            isDataLoaded = true
            return true
        }
        return false
    }

    abstract fun getLayoutId(): Int

    abstract fun loadData()

    abstract fun init(savedInstanceState: Bundle?)


}
