package com.practice.eyepetizer.mvp.ui.activity

import android.view.KeyEvent
import android.view.View
import cn.yt.demo_kotlin.R
import com.practice.eyepetizer.base.BaseActivity
import com.practice.eyepetizer.utils.StatusBarUtil
import com.practice.eyepetizer.widget.X5WebView
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.activity_personalhomepage.*

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/24.
 */
class PersonalHomepageActivity : BaseActivity() {
    internal var url = "https://github.com/creativityingenuity/DayDayStudy"
    var x5View: X5WebView? = null

    override fun getLayoutId(): Int = R.layout.activity_personalhomepage

    override fun initData() {
        initHardwareAccelerate()
    }

    override fun initView() {
        //状态栏透明处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        //webview设置
        x5View = webview as X5WebView
        x5View?.loadUrl(url)
        x5View?.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(webView: WebView?, i: Int) {
                super.onProgressChanged(webView, i)
                if (i < 100) {
                    loading.progress = i
                } else {
                    loading.visibility = View.GONE
                }
            }
        })

        //toolbar
        buttonBarLayout.alpha = 0.7f
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun start() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != x5View) x5View?.destroy()
    }

    /**
     * 返回键监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (x5View != null && x5View!!.canGoBack()) {
                x5View?.goBack()
                return true
            } else {
                return super.onKeyDown(keyCode, event)
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 启用硬件加速
     */
    private fun initHardwareAccelerate() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                window.setFlags(
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
            }
        } catch (e: Exception) {
        }

    }
}