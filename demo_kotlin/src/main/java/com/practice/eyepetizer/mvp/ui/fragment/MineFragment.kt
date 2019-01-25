package com.practice.eyepetizer.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.base.BaseFragment
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.globle.showToast
import com.practice.eyepetizer.mvp.ui.activity.PersonalHomepageActivity
import com.practice.eyepetizer.mvp.ui.activity.WatchHistoryActivity
import com.practice.eyepetizer.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/12/11.
 * 我的
 */
class MineFragment : BaseFragment(), View.OnClickListener {

    var title: String? = null

    companion object {
        fun getInstance(title: String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            bundle.putString(Constants.FRAGMENT_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun loadData() {

    }

    override fun init(savedInstanceState: Bundle?) {
        //title
        title = arguments?.getString(Constants.FRAGMENT_TITLE)
        activity?.let {
            StatusBarUtil.darkMode(it)
            StatusBarUtil.setPaddingSmart(it, toolbar)
        }

        tv_view_homepage.setOnClickListener(this)
        iv_avatar.setOnClickListener(this)
        iv_about.setOnClickListener(this)

        tv_collection.setOnClickListener(this)
        tv_comment.setOnClickListener(this)

        tv_mine_message.setOnClickListener(this)
        tv_mine_attention.setOnClickListener(this)
        tv_mine_cache.setOnClickListener(this)
        tv_watch_history.setOnClickListener(this)
        tv_feedback.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_avatar, R.id.tv_view_homepage -> {
                val intent = Intent(activity, PersonalHomepageActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_about -> {
                val intent = Intent(activity, PersonalHomepageActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_collection -> showToast("收藏")
            R.id.tv_comment -> showToast("评论")
            R.id.tv_mine_message -> showToast("我的消息")
            R.id.tv_mine_attention -> showToast("我的关注")
            R.id.tv_mine_attention -> showToast("我的缓存")
            R.id.tv_watch_history -> startActivity(Intent(activity, WatchHistoryActivity::class.java))
            R.id.tv_feedback -> showToast("意见反馈")
        }
    }
}