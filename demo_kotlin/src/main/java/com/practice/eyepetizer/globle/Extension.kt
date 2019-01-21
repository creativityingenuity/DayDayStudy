package com.practice.eyepetizer.globle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
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

fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(GlobleApplication.context, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

//格式化时间
fun durationFormat(duration: Long): String {
    val minute = duration / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}