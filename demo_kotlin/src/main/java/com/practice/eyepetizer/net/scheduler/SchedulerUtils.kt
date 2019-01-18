package com.practice.eyepetizer.net.scheduler

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/18.
 */
object SchedulerUtils {
    fun <T> ioToMain(): IoMainScheduler<T>{
        return IoMainScheduler()
    }
}