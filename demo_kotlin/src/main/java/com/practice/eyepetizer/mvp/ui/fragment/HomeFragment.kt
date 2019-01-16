package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.mvp.presenter.HomePresenter
import com.tt.lvruheng.eyepetizer.ui.fragment.BaseFragment

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/11.
 * 首页
 */
class HomeFragment : BaseFragment() {
    val mPresenter by lazy { HomePresenter() }
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

    override fun loadData() {

    }

    override fun init(savedInstanceState: Bundle?) {

    }
}