package com.practice.eyepetizer.mvp.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import cn.yt.demo_kotlin.R
import com.flyco.tablayout.listener.CustomTabEntity
import com.practice.eyepetizer.base.BaseActivity
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.mvp.model.bean.TabEntity
import com.practice.eyepetizer.mvp.ui.fragment.FindFragment
import com.practice.eyepetizer.mvp.ui.fragment.HomeFragment
import com.practice.eyepetizer.mvp.ui.fragment.HotFragment
import com.practice.eyepetizer.mvp.ui.fragment.MineFragment
import java.util.*

class MainActivity : BaseActivity() {
    //底部文字
    private val mTitles = arrayOf("首页", "发现", "热门", "我的")
    //默认为0
    private var mIndex = 0
    // 未被选中的图标
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_home_normal, R.mipmap.ic_discovery_normal, R.mipmap.ic_hot_normal, R.mipmap.ic_mine_normal)
    // 被选中的图标
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_home_selected, R.mipmap.ic_discovery_selected, R.mipmap.ic_hot_selected, R.mipmap.ic_mine_selected)
    private val mTabEntities = ArrayList<CustomTabEntity>()
    var mHomeFragment: HomeFragment? = null
    var mFindFragment: FindFragment? = null
    var mHotFragemnt: HotFragment? = null
    var mMineFragment: MineFragment? = null
    var mExitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        val mIndex = savedInstanceState?.getInt(Constants.CURRTABINDEX)
        super.onCreate(savedInstanceState)
        initTab()

    }
    override fun getLayoutId(): Int {
        return R.layout.activity_main2
    }
    /**
     * 初始化底部tab
     */
    private fun initTab() {
        (0 until mTitles.size).mapTo(mTabEntities){
            TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it])
        }
    }



    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 3000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
