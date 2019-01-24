package com.practice.eyepetizer.mvp.ui.activity

import android.support.v7.widget.LinearLayoutManager
import cn.yt.demo_kotlin.R
import com.hazz.kotlinmvp.GlobleApplication

import com.practice.eyepetizer.base.BaseActivity
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.mvp.model.bean.HomeBean
import com.practice.eyepetizer.mvp.ui.adapter.WatchHistoryAdapter
import com.practice.eyepetizer.utils.StatusBarUtil
import com.practice.eyepetizer.utils.WatchHistoryUtils
import kotlinx.android.synthetic.main.layout_watch_history.*
import java.util.*

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/24.
 * 观看记录页面
 */
class WatchHistoryActivity : BaseActivity() {
    var itemListData = ArrayList<HomeBean.Issue.Item>()

    override fun getLayoutId(): Int = R.layout.layout_watch_history

    override fun initView() {
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)

        toolbar.setNavigationOnClickListener { finish() }

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val mAdapter = WatchHistoryAdapter(this, itemListData, R.layout.item_video_small_card)
        mRecyclerView.adapter = mAdapter

        if(itemListData.size>0){
            multipleStatusView.showContent()
        }else{
            multipleStatusView.showEmpty()
        }
    }


    override fun initData() {
       multipleStatusView.showLoading()
        itemListData = getServerData()
    }

    /**
     * 请求服务器数据 获取观看的历史记录
     */
    private fun getServerData(): ArrayList<HomeBean.Issue.Item> {
        val watchList = ArrayList<HomeBean.Issue.Item>()
       val hisAll =  WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME, GlobleApplication.context) as Map<*, *>
        //将key圣墟
        var keys = hisAll.keys.toTypedArray()
        Arrays.sort(keys)
        val keyLength = keys.size
        //这里计算 如果历史记录条数是大于 可以显示的最大条数，则用最大条数做循环条件，防止历史记录条数-最大条数为负值，数组越界
        val hisLength = if (keyLength > 20) 20 else keyLength
        // 反序列化和遍历 添加观看的历史记录
        (1..hisLength).mapTo(watchList) {
            WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME, GlobleApplication.context,
                    keys[keyLength - it] as String) as HomeBean.Issue.Item
        }

        return watchList
    }

    override fun start() {

    }
}