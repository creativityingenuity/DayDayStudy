package com.yt.daydaystudy.demo_kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yt.daydaystudy.R

class KotlinDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo)
    }

    //一部分是静态方法 将方法用此包裹即可
    companion object {
        fun startAction(activity: Activity){
            activity.startActivity(Intent(activity,KotlinDemoActivity::class.java))
        }
    }
}
