package com.tt.lvruheng.eyepetizer.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {
    var mActivity: Context? = null
    var rootView: View? = null
    var isViewInitiated: Boolean = false
    var isDataLoaded: Boolean = false
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (rootView == null) {
            rootView = inflater?.inflate(getLayoutId(), container, false)
        }
        return rootView
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewInitiated = true
        init(savedInstanceState)
        prepareFetchData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        prepareFetchData()
    }

    private fun prepareFetchData() : Boolean {
        return prepareFetchData(false)
    }

    private fun prepareFetchData(forceUpdate: Boolean): Boolean {
        if(userVisibleHint && isViewInitiated &&(!isDataLoaded || forceUpdate)){
            loadData()
            isDataLoaded = true
            return true
        }
        return false
    }

    /*子类实现*/
    abstract fun getLayoutId(): Int

    abstract fun loadData()

    abstract fun init(savedInstanceState: Bundle?)


}