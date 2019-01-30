package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.base.BaseFragment
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.globle.showToast
import com.practice.eyepetizer.mvp.contract.HotContract
import com.practice.eyepetizer.mvp.model.bean.TabInfoBean
import com.practice.eyepetizer.mvp.presenter.HotPresenter
import com.practice.eyepetizer.mvp.ui.adapter.BaseFragmentAdapter
import com.practice.eyepetizer.net.exception.ErrorStatus
import com.practice.eyepetizer.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/11.
 * 热门
 */
class HotFragment : BaseFragment(), HotContract.View {
    /**
     * title
     */
    var mTabTitleList = ArrayList<String>()
    /**
     * fragment列表
     */
    var mFragmentList = ArrayList<Fragment>()
    private val mPresenter by lazy { HotPresenter() }

    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            bundle.putString(Constants.FRAGMENT_TITLE,title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun init(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        activity?.let {
            StatusBarUtil.darkMode(it)
            StatusBarUtil.setPaddingSmart(it, toolbar)
        }
        var title = arguments?.getString(Constants.FRAGMENT_TITLE)
        tv_header_title.text = title
        mLayoutStatusView = multipleStatusView
    }

    override fun loadData() {
        //获取tablayout标题
        mPresenter.getTabInfo()
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {

    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        mLayoutStatusView?.showContent()


        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList) {
            it.name
        }
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) {
            RankFragment.getInstance(it.apiUrl)
        }

        mViewPager.adapter =  BaseFragmentAdapter(childFragmentManager,mFragmentList,mTabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)
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