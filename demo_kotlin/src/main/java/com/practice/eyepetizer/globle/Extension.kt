package com.practice.eyepetizer.globle

import android.app.Activity
import android.content.Intent

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/8.
 * 全局helper
 */
inline fun <reified T : Activity> Activity.newIntent() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}
