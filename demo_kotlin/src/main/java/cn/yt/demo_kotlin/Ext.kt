package cn.yt.demo_kotlin

import android.content.Context
import android.widget.Toast

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/11/30.
 */
class Ext {
    /**
     * 函数扩展
     *  fun 类型.函数(参数)
     */
    fun Context.toast(message:String,length:Int = Toast.LENGTH_LONG){
        Toast.makeText(this,message,length)
    }
}