package com.practice.eyepetizer.mvp.ui.fragment

import android.os.Bundle
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.base.BaseFragment
import com.practice.eyepetizer.globle.Constants

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/25.
 */
class RankFragment : BaseFragment() {
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

    override fun loadData() {
    }

    override fun init(savedInstanceState: Bundle?) {
        var url = arguments?.getString(Constants.RANKURL)
    }
}