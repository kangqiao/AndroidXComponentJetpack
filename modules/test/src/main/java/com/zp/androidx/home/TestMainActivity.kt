package com.zp.androidx.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zp.androidx.base.arch.BaseActivity
import com.zp.androidx.base.config.RouterConfig
import com.zp.androidx.ext.toast.AnimationUtils
import com.zp.androidx.ext.toast.XToast
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
        Timber.d("进入Test")

        setContent {
            MaterialTheme {
                Greeting(this, "Zhaopan")
            }
        }
    }
}


fun testXToast(activity: Activity) {
    XToast.create(activity)
        .setText("Testing:This is a XToast....")
        .setAnimation(AnimationUtils.ANIMATION_DRAWER) //抽屉式效果
        .setDuration(XToast.XTOAST_DURATION_SHORT)
        .setOnDisappearListener(XToast.OnDisappearListener {
            Log.d("cylog","The XToast has disappeared..");
        }).show();
}

@Composable
fun Greeting(activity: Activity, name: String) {

    Clickable(onClick = {
        testXToast(activity)
    }) {
        Text(text = "Hello $name!")
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        //Greeting("Android")
    }
}