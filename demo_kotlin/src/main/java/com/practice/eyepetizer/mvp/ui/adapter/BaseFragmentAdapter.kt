package com.practice.eyepetizer.mvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.practice.eyepetizer.base.BaseFragment

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/25.
 */
class BaseFragmentAdapter(fm: FragmentManager?, framents: ArrayList<BaseFragment>, titles: ArrayList<String>) : FragmentPagerAdapter(fm) {
    var mFragmentList = ArrayList<BaseFragment>()
    var mTabTitleList = ArrayList<String>()

    init {
        mFragmentList = framents
        mTabTitleList = titles
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTabTitleList[position]
    }
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mTabTitleList.size
    }
}