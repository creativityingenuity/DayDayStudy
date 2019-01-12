package com.practice.eyepetizer.globle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.hazz.kotlinmvp.GlobleApplication

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/8.
 * 全局helper
 */
inline fun <reified T : Activity> Activity.newIntent() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

fun Context.showToast(content: String): Toast {
    val toast = Toast.makeText(GlobleApplication.context, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}