package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.base.BaseFragment
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.globle.showToast
import com.practice.eyepetizer.mvp.contract.RankContract
import com.practice.eyepetizer.mvp.model.bean.HomeBean
import com.practice.eyepetizer.mvp.presenter.RankPresenter
import com.practice.eyepetizer.mvp.ui.adapter.CategoryDetailAdapter
import com.practice.eyepetizer.net.exception.ErrorStatus
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/25.
 * 热门tab下面的三个界面用的一个
 */
class RankFragment : BaseFragment(), RankContract.View {
    /**
     * 每个界面的url
     */
    private var url: String? = null
    private val mPresenter by lazy { RankPresenter() }
    private var itemList = ArrayList<HomeBean.Issue.Item>()
    private val mAdapter by lazy { activity?.let { CategoryDetailAdapter(it, itemList, R.layout.item_category_detail) } }
    override fun getLayoutId(): Int = R.layout.fragment_rank

    companion object {
        fun getInstance(url: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            bundle.putString(Constants.RANKURL, url)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        url = arguments?.getString(Constants.RANKURL)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
        mLayoutStatusView = multipleStatusView
    }

    override fun loadData() {
        url?.let { mPresenter.requestRankList(it) }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
    }

    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        multipleStatusView.showContent()
        mAdapter?.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}