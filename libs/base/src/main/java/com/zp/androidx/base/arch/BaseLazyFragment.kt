package com.zp.androidx.base.arch

import androidx.fragment.app.Fragment
import timber.log.Timber

/**
 * Create by zhaopan on 2020/7/2
 */
open abstract class BaseLazyFragment: Fragment() {

    private var isLoaded = false

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyInit();
            isLoaded = true
            Timber.d("BaseLazyFragment lazyInit...")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    abstract fun lazyInit()
}