package com.zp.androidx.base.arch

import androidx.fragment.app.*
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Create by zhaopan on 2020/7/2
 */

open class FragmentLazyPagerAdapter(
    fragmentManager: FragmentManager,
    private val titles: MutableList<String>,
    private val fragments: MutableList<Fragment>
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = fragments[position % fragments.size]

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int) =  titles[position % titles.size]
}

open class FragmentLazyStatePageAdapter(
    fragmentManager: FragmentManager,
    private val titles: MutableList<String>,
    private val fragments: MutableList<Fragment>
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = titles[position]

}

class FragmentLazyStateAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: MutableList<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}
