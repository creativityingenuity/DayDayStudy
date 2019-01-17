package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.mvp.contract.HomeContract
import com.practice.eyepetizer.mvp.presenter.HomePresenter
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.ui.fragment.BaseFragment
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
    private var isRefresh :Boolean = false
    private var mMaterialHeader: MaterialHeader? = null
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

    //todo ?
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
        //是否下拉Header的时候向下平移列表或者内容
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(num)
        }
    }

    override fun loadData() {

    }


    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun setHomeData(homeBean: HomeBean) {
    }
}