package com.practice.eyepetizer.mvp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import cn.yt.demo_kotlin.R
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.practice.eyepetizer.base.BaseActivity
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.globle.showToast
import com.practice.eyepetizer.mvp.model.bean.TabEntity
import com.practice.eyepetizer.mvp.ui.fragment.FindFragment
import com.practice.eyepetizer.mvp.ui.fragment.HomeFragment
import com.practice.eyepetizer.mvp.ui.fragment.HotFragment
import com.practice.eyepetizer.mvp.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main2.*
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
    var mHotFragment: HotFragment? = null
    var mMineFragment: MineFragment? = null
    //默认设置为第一个展示
    var mExitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt(Constants.CURRTABINDEX)
        }
        super.onCreate(savedInstanceState)
        initTab()
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main2
    }

    /**
     * 初始化底部tab
     */
    private fun initTab() {
        (0 until mTitles.size).mapTo(mTabEntities) {
            TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it])
        }
        //为tan赋值
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                //切换fragment
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {
            }
        })
    }

    /**
     * 切换fragment
     * @param position 下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 ->
                mHomeFragment?.let {
                    //在一个作用域中对一个对象统一判null
                    //如果mHomeFragment不为null 才会执行let函数体
                    transaction.show(it)
                } ?: HomeFragment.getInstance(mTitles[0]).let {
                    mHomeFragment = it
                    transaction.add(R.id.fl_container, it, HomeFragment::class.java.simpleName)
                }
            1 ->
                mFindFragment?.let {
                    transaction.show(it)
                } ?: FindFragment.getInstance(mTitles[1]).let {
                    mFindFragment = it
                    transaction.add(R.id.fl_container, it, FindFragment::class.java.simpleName)
                }
            2 ->
                mHotFragment?.let {
                    transaction.show(it)
                } ?: HotFragment.getInstance(mTitles[position]).let {
                    mHotFragment = it
                    transaction.add(R.id.fl_container, it, HotFragment::class.java.simpleName)
                }
            3 ->
                mMineFragment?.let {
                    transaction.show(it)
                } ?: MineFragment.getInstance(mTitles[position]).let {
                    mMineFragment = it
                    transaction.add(R.id.fl_container, it, MineFragment::class.java.simpleName)
                }
        }
        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commit()
    }

    /**
     * 隐藏所有的fragment
     */
    private fun hideFragments(transaction: FragmentTransaction?) {
        mHomeFragment?.let {
            transaction?.hide(it)
        }
        mFindFragment?.let { transaction?.hide(it) }
        mHotFragment?.let { transaction?.hide(it) }
        mMineFragment?.let { transaction?.hide(it) }
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        //记录fragment的位置,防止崩溃 activity被系统回收时，fragment错乱
        if (tab_layout != null) {
            outState.putInt(Constants.CURRTABINDEX, mIndex)
        }
    }
    override fun initData() {
    }

    override fun initView() {
    }

    override fun start() {
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 3000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
