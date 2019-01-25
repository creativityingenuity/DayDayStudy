package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.yt.demo_kotlin.R
import com.orhanobut.logger.Logger
import com.practice.eyepetizer.base.BaseFragment
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.globle.newIntent
import com.practice.eyepetizer.globle.showToast
import com.practice.eyepetizer.mvp.contract.HomeContract
import com.practice.eyepetizer.mvp.model.bean.HomeBean
import com.practice.eyepetizer.mvp.presenter.HomePresenter
import com.practice.eyepetizer.mvp.ui.activity.SearchActivity
import com.practice.eyepetizer.mvp.ui.adapter.HomeAdatper
import com.practice.eyepetizer.net.exception.ErrorStatus
import com.practice.eyepetizer.utils.StatusBarUtil
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/11.
 * 首页
 */
class HomeFragment : BaseFragment(), HomeContract.View {
    val mPresenter by lazy { HomePresenter() }
    /**
     * 当前请求的页
     */
    private var num: Int = 1
    /**
     * 是否刷新
     */
    private var isRefresh: Boolean = false
    private var loadingMore: Boolean = false
    private var mHomeAdapter: HomeAdatper? = null

    override fun getLayoutId(): Int = R.layout.home_fragment

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString(Constants.FRAGMENT_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    /**
     * 初始化view
     */
    override fun init(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mRefreshLayout.setRefreshHeader(ClassicsHeader(activity))
        //下拉刷新
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(num)
            mRefreshLayout.finishRefresh(1000)
        }
        //加载更多
        mRefreshLayout.setOnLoadmoreListener {
            mRefreshLayout.finishLoadmore(2000)//传入false表示加载失败
        }


        //recyclerview 滚动效果
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        })

        iv_search.setOnClickListener {
            openSearchActivity()
        }
        mLayoutStatusView = multipleStatusView

        //状态栏透明和间距处理
        activity?.let {
            StatusBarUtil.darkMode(it)
            StatusBarUtil.setPaddingSmart(it, toolbar)
        }
    }

    /**
     * 打开搜索界面
     */
    private fun openSearchActivity() {
       activity?.newIntent<SearchActivity>()
    }

    /**
     * 加载数据
     */
    override fun loadData() {
        mPresenter.requestHomeData(num)
    }


    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun showLoading() {
        if (!isRefresh) {
            isRefresh = false
            mLayoutStatusView?.showLoading()
        }
    }

    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
    }

    override fun setHomeData(homeBean: HomeBean) {
        mLayoutStatusView?.showContent()
        Logger.d(homeBean)

        //adapter
        mHomeAdapter = activity?.let { HomeAdatper(it, homeBean.issueList[0].itemList) }
        mHomeAdapter?.setBannerSize(homeBean.issueList[0].count)
        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = true
        mHomeAdapter?.addItemData(itemList)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }
}