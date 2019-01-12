package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.mvp.presenter.HomePresenter
import com.practice.eyepetizer.mvp.ui.adapter.HomeAdatper
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.ui.fragment.BaseFragment
import java.util.*

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/11.
 * 首页
 */
class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    var mIsRefresh: Boolean = false
    var mPresenter: HomePresenter? = null
    var mList = ArrayList<HomeBean.IssueListBean.ItemListBean>()
    var mAdapter: HomeAdatper? = null
    var data: String? = null
    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString(Constants.FRAGMENT_TITLE,title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun loadData() {

    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun onRefresh() {
    }
}