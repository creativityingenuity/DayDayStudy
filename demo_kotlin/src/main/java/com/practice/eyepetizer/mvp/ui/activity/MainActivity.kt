package com.practice.eyepetizer.mvp.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import cn.yt.demo_kotlin.R
import com.gyf.barlibrary.ImmersionBar
import com.practice.eyepetizer.globle.showToast
import com.practice.eyepetizer.mvp.ui.fragment.FindFragment
import com.practice.eyepetizer.mvp.ui.fragment.HomeFragment
import com.practice.eyepetizer.mvp.ui.fragment.HotFragment
import com.practice.eyepetizer.mvp.ui.fragment.MineFragment
import com.tt.lvruheng.eyepetizer.search.SEARCH_TAG
import com.tt.lvruheng.eyepetizer.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var homeFragment: HomeFragment? = null
    var findFragment: FindFragment? = null
    var hotFragemnt: HotFragment? = null
    var mineFragment: MineFragment? = null
    var mExitTime: Long = 0
    var toast: Toast? = null
    lateinit var searchFragment: SearchFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        val window = window
        var attributes = window.attributes
        attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes = attributes

        setRadioButton()
        initToolBar()
        initFragment(savedInstanceState)
    }

    /**
     * 初始化fragment
     */
    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            homeFragment = HomeFragment()
            findFragment = FindFragment()
            mineFragment = MineFragment()
            hotFragemnt = HotFragment()
            var transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fl_content, homeFragment)
            transaction.add(R.id.fl_content, findFragment)
            transaction.add(R.id.fl_content, mineFragment)
            transaction.add(R.id.fl_content, hotFragemnt)
            transaction.commit()
        } else {
            //异常情况
            var fragments = supportFragmentManager.fragments
            for (item in fragments) {
                if (item is HomeFragment) {
                    homeFragment = item
                }
                if (item is FindFragment) {
                    findFragment = item
                }
                if (item is HotFragment) {
                    hotFragemnt = item
                }
                if (item is MineFragment) {
                    mineFragment = item
                }
            }
        }
        supportFragmentManager.beginTransaction().show(homeFragment)
                .hide(findFragment)
                .hide(mineFragment)
                .hide(hotFragemnt)
                .commit()
    }

    private fun initToolBar() {
        var today = getToday()
        tv_bar_title.text = today
        tv_bar_title.typeface = Typeface.createFromAsset(this.assets, "font/Lobster-1.4.otf")
        iv_search.setOnClickListener {
            if (rb_mine.isChecked) {

            } else {
                searchFragment = SearchFragment()
                searchFragment.show(fragmentManager, SEARCH_TAG)
            }
        }
    }

    /**
     * 获取今天是周几
     */
    private fun getToday(): String {
        var list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        var date: Date = Date()
        var instance = Calendar.getInstance()
        instance.time = date
        var index = instance.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index = 0
        }
        return list[index]
    }

    private fun setRadioButton() {
        rb_home.isChecked = true
        rb_home.setTextColor(resources.getColor(R.color.black))
        rb_home.setOnClickListener(this)
        rb_find.setOnClickListener(this)
        rb_hot.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
    }

    override fun onPause() {
        super.onPause()
        toast?.let { toast!!.cancel() }
    }

    override fun onClick(v: View?) {
        clearState()
        when(v?.id){
            R.id.rb_find ->{
                rb_find.isChecked = true
                rb_find.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(findFragment)
                        .hide(homeFragment)
                        .hide(mineFragment)
                        .hide(hotFragemnt)
                        .commit()
                tv_bar_title.text = "Discover"
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
            }
            R.id.rb_home -> {
                rb_home.isChecked = true
                rb_home.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(homeFragment)
                        .hide(findFragment)
                        .hide(mineFragment)
                        .hide(hotFragemnt)
                        .commit()
                tv_bar_title.text = getToday()
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
            }
            R.id.rb_hot -> {
                rb_hot.isChecked = true
                rb_hot.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(hotFragemnt)
                        .hide(findFragment)
                        .hide(mineFragment)
                        .hide(homeFragment)
                        .commit()
                tv_bar_title.text = "Ranking"
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
            }
            R.id.rb_mine -> {
                rb_mine.isChecked = true
                rb_mine.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(mineFragment)
                        .hide(findFragment)
                        .hide(homeFragment)
                        .hide(hotFragemnt)
                        .commit()
                tv_bar_title.visibility = View.INVISIBLE
                iv_search.setImageResource(R.drawable.icon_setting)
            }
        }
    }

    private fun clearState() {
        rg_root.clearCheck()
        rb_home.setTextColor(resources.getColor(R.color.gray))
        rb_mine.setTextColor(resources.getColor(R.color.gray))
        rb_hot.setTextColor(resources.getColor(R.color.gray))
        rb_find.setTextColor(resources.getColor(R.color.gray))
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 3000) {
                finish()
                toast!!.cancel()
            } else {
                mExitTime = System.currentTimeMillis()
                toast = showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
