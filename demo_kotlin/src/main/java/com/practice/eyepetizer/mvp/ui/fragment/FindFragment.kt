package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.base.BaseFragment
import com.practice.eyepetizer.mvp.ui.adapter.BaseFragmentAdapter
import com.practice.eyepetizer.utils.StatusBarUtil
import com.practice.eyepetizer.widget.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/11.
 */
class FindFragment : BaseFragment() {



    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): FindFragment {
            val fragment = FindFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot

override fun init(savedInstanceState: Bundle?) {

        //状态栏透明和间距处理
    activity?.let {
        StatusBarUtil.darkMode(it)
        StatusBarUtil.setPaddingSmart(it, toolbar)
    }

        tv_header_title.text = mTitle

        tabList.add("关注")
        tabList.add("分类")
        fragments.add(FollowFragment.getInstance("关注"))
        fragments.add(CategoryFragment.getInstance("分类"))





    }

override fun loadData()  {

    /**
     * getSupportFragmentManager() 替换为getChildFragmentManager()
     */
    mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
    mTabLayout.setupWithViewPager(mViewPager)
    TabLayoutHelper.setUpIndicatorWidth(mTabLayout)
    }

}