package com.hazz.kotlinmvp

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

class GlobleApplication : Application(){

    companion object {
        private val TAG = "GlobleApplication"
        var context: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
