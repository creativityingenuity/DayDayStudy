package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.base.BaseFragment
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.mvp.ui.adapter.BaseFragmentAdapter
import com.practice.eyepetizer.utils.StatusBarUtil
import com.practice.eyepetizer.widget.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/11.
 */
class FindFragment : BaseFragment() {

    override fun getLayoutId(): Int  = R.layout.fragment_hot

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

    override fun init(savedInstanceState: Bundle?) {
        //处理状态栏透明和间距处理
        activity?.let {
            StatusBarUtil.darkMode(it)
            StatusBarUtil.setPaddingSmart(it, toolbar)
        }
        var title = arguments?.getString(Constants.FRAGMENT_TITLE)
        tv_header_title.text = title

        var framents = arrayListOf(FollowFragment.getInstance("关注"),CategoryFragment.getInstance("分类"))
        var tabTitle = arrayListOf<String>("关注","分类")
        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager,framents,tabTitle)

        mTabLayout.setupWithViewPager(mViewPager)
        TabLayoutHelper.setUpIndicatorWidth(mTabLayout)
    }


    override fun loadData() {
    }


}