package com.zp.androidx.app

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationView
import com.zp.androidx.app.ui.HomeFragment
import com.zp.androidx.app.ui.KnowledgeFragment
import com.zp.androidx.app.ui.ProjectFragment
import com.zp.androidx.app.ui.WeChatFragment
import com.zp.androidx.base.arch.BaseActivity
import com.zp.androidx.base.arch.loadFragments
import com.zp.androidx.base.arch.showHideFragment
import com.zp.androidx.base.config.RouterConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by zhaopan on 2019-10-15.
 */

@Route(path = RouterConfig.APP.MAIN, name = "App首页")
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val TAG = "MainActivity"
        const val BOTTOM_INDEX = "bottom_index"
        const val FIRST = 0
        const val SECOND = 1
        const val THIRD = 2
        const val FOURTH = 3
    }
    private val mFragments = arrayOfNulls<Fragment>(4)
    private var currentNavController: LiveData<NavController>? = null
    private lateinit var tvNavUsername: TextView
    private var currentTab = FIRST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // val homeFragment = ARouter.getInstance().build(RouterConfig.Home.HOME).navigation() as Fragment
        // val knowledgeFragment = ARouter.getInstance().build(RouterConfig.Knowledge.HOME).navigation() as Fragment
        // val projectFragment = ARouter.getInstance().build(RouterConfig.Project.HOME).navigation() as Fragment
        // val wechatFragment = ZPFlutterFragment.newInstance(RouterConfig.Flutter.Router.WeChat)
        // val firstFragment: Fragment? = supportFragmentManager.findFragmentByTag(homeFragment.javaClass)

        val homeFragment = HomeFragment()
        val knowledgeFragment = KnowledgeFragment()
        val projectFragment = ProjectFragment()
        val wechatFragment = WeChatFragment()
        Log.d(
            "zp:::",
            " homeFragment = ${homeFragment} class = ${homeFragment.javaClass} savedInstanceState = ${savedInstanceState}"
        )
        if (savedInstanceState == null) {
            mFragments[FIRST] = homeFragment
            mFragments[SECOND] = knowledgeFragment
            mFragments[THIRD] = projectFragment
            mFragments[FOURTH] = wechatFragment//MineFragment.newInstance()
        } else {
            currentTab = savedInstanceState.getInt(BOTTOM_INDEX)
        }
        loadFragments(
            R.id.container,
            currentTab,
            homeFragment, knowledgeFragment, projectFragment, wechatFragment
        )
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        currentNavController = MutableLiveData<NavController>()

        val tabIdArray = arrayOf(R.id.action_home, R.id.action_knowledge_system, R.id.action_project, R.id.action_wechat)

        bottom_navigation.run {
            // 以前使用 BottomNavigationViewHelper.disableShiftMode(this) 方法来设置底部图标和字体都显示并去掉点击动画
            // 升级到 28.0.0 之后，官方重构了 BottomNavigationView ，目前可以使用 labelVisibilityMode = 1 来替代
            // BottomNavigationViewHelper.disableShiftMode(this)
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            setOnNavigationItemSelectedListener {
                val selectTab = tabIdArray.indexOf(it.itemId)
                if (selectTab in FIRST..FOURTH) {
                    if (selectTab != currentTab) { //切换Tab
                        showHideFragment(mFragments[selectTab]!!)
                        toolbar.title = it.title
                        currentTab = selectTab
                    } else { //重复选择当前Tab, TabReselected.
                        mFragments[selectTab]?.run {
                            if (supportFragmentManager.backStackEntryCount > 1) { //说明当前Tab非引模块首页, fragment回退栈数量大于1
                                // todo Do what you can do
                            }
                        }
                    }
                    return@setOnNavigationItemSelectedListener true
                } else {
                    return@setOnNavigationItemSelectedListener false
                }
            }
            toolbar.title = menu.findItem(tabIdArray[currentTab]).title
            //selectedItemId = tabIdArray[currentTab]  // error: Fragment has not been attached yet.
        }

        drawer_layout.run {
            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }

        nav_view.run {
            setNavigationItemSelectedListener(this@MainActivity)
            tvNavUsername = getHeaderView(0).findViewById(R.id.tv_username)
            menu.findItem(R.id.nav_logout).isVisible = true//userService.isLogin()
        }
        tvNavUsername?.run {
//            text = if (!userService.isLogin()) getString(R.string.login) else userService.getUserName()
//            setOnClickListener {
//                if (!userService.isLogin()) {
//                    ARouter.getInstance().build(RouterConfig.User.LOGIN).navigation()
//                } else {
//                }
//            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putInt(BOTTOM_INDEX, currentTab)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        initView(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                //SearchActivity.open()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_collect -> {
                ARouter.getInstance().build(RouterConfig.User.COLLECT_LIST).navigation()
            }
            R.id.nav_setting -> {

            }
            R.id.nav_about_us -> {

            }
            R.id.nav_logout -> {
                /*userService.logout(object: HandleCallBack<Boolean> {
                    override fun onResult(result: BackResult<Boolean>) {
                        //toast(result.message)
                    }
                })*/
            }
            R.id.nav_night_mode -> {

            }
            R.id.nav_todo -> {

            }
        }
        return true
    }
}