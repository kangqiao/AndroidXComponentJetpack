package com.zp.androidx.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zp.androidx.app.R
import com.zp.androidx.base.arch.BaseLazyFragment
import kotlinx.android.synthetic.main.fragment_test.*

/**
 * Create by zhaopan on 2020/7/2
 */

open class TestFragment : BaseLazyFragment() {
    companion object {
        fun newInstance(): Fragment {
            return WeChatFragment().apply {
                arguments = Bundle()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }


    override fun lazyInit() {
        tv_test.text = this.javaClass.simpleName
    }
}

class HomeFragment: TestFragment()

class KnowledgeFragment: TestFragment()

class ProjectFragment: TestFragment()

class WeChatFragment: TestFragment()