package com.zp.androidx.home

import android.os.Bundle
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zp.androidx.base.arch.BaseActivity
import com.zp.androidx.base.config.RouterConfig
import timber.log.Timber

/**
 * Created by zhaopan on 2020/5/10
 */

@Route(path = RouterConfig.TEST.MAIN, name = "Test首页")
class TestMainActivity: BaseActivity() {
    companion object {
        fun open(){
            ARouter.getInstance().build(RouterConfig.TEST.MAIN).navigation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSwipeBackEnable(false)
        Timber.d("进入Test")

        setContent {
            MaterialTheme {
                Greeting("Zhaopan")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Greeting("Android")
    }
}