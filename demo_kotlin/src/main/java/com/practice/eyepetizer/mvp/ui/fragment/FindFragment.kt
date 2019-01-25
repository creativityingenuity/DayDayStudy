package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import com.practice.eyepetizer.base.BaseFragment
import com.practice.eyepetizer.globle.Constants

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/11.
 */
class FindFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 类似静态操作
     */
    companion object {
        fun getInstance(title : String) : FindFragment{
            val fragment = FindFragment()
            val bundle = Bundle()
            bundle.putString(Constants.FRAGMENT_TITLE,title)
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun loadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun init(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRefresh() {
    }
}