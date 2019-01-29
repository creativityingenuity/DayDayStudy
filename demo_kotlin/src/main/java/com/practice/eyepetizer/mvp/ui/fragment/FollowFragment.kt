package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.base.BaseFragment
import com.practice.eyepetizer.globle.Constants

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/29.
 */
class FollowFragment : BaseFragment(){

    companion object {
        fun getInstance(title: String): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            bundle.putString(Constants.FRAGMENT_TITLE,title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_recyclerview

    override fun loadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun init(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}