package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.utils.StatusBarUtil
import com.tt.lvruheng.eyepetizer.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/11.
 * 热门
 */
class HotFragment : BaseFragment() {
    private val mPresenter by lazy {HotPresenter() }

    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun init(savedInstanceState: Bundle?) {
        activity?.let {
            StatusBarUtil.darkMode(it)
            StatusBarUtil.setPaddingSmart(it, toolbar)
        }
        mLayoutStatusView = multipleStatusView
    }

    override fun loadData() {
        //获取tablayout标题

    }


}