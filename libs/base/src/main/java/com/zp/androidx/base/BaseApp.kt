package com.zp.androidx.base

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDexApplication
import timber.log.Timber

/**
 * Created by zhaopan on 2020/5/10
 */

// 模块需要实现的初始化接口
interface ModuleInitializer{
    fun initModuleApp(application: Application)
    fun initModuleData(application: Application)
}

abstract class BaseApp: MultiDexApplication() {
    companion object {
        const val TAG = "BaseApp"
        lateinit var application: Application private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        application = this
    }

    override fun onCreate() {
        super.onCreate()
        // BaseApp中初始化操作

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
}

abstract class MainApp: BaseApp() {
    override fun onCreate() {
        super.onCreate()


    }
}

abstract class ModuleApp: BaseApp(), ModuleInitializer {

}

/**
 * 日志处理类
 */
class CrashReportingTree: Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if(BuildConfig.DEBUG || Log.isLoggable("zp", Log.DEBUG)){
            super.log(priority, tag, message, t);
        }
    }

}