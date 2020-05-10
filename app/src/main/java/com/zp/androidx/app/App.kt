package com.zp.androidx.app

import android.app.Application

/**
 * Created by zhaopan on 2020/5/10
 */

class App: Application() {
    companion object {
        const val TAG = "APP"
    }

    override fun onCreate() {
        super.onCreate()

    }
}