package com.example.accountsbook

import android.os.Bundle
import com.example.accountsbook.navigation.Navigator
import com.example.accountsbook.navigation.Tab
import com.example.accountsbook.navigation.TabContainerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity(), TabContainerFragment.FragmentChangedListener, Navigator {

    companion object {

        const val KEY_CURRENT_TAB_TAG = "KEY_CURRENT_TAB_TAG"
    }

    private lateinit var bottomNavigationView: BottomNavigationView
    private val tabContainerMap = mutableMapOf<Tab, TabContainerFragment>()
    private var currentContainerFragment: TabContainerFragment? = null
    private var currentTab: Tab? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        initFragmentContainer(savedInstanceState)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener Tab.values()
                .firstOrNull { tab -> it.itemId == tab.menuId }
                ?.let {
                    switchTabContainerFragment(it)
                    true
                } ?: false
        }
    }

    override fun onFragmentChanged(fragment: BaseFragment) {

    }

    private fun initFragmentContainer(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            Tab.values().forEach {
                tabContainerMap[it] =
                    supportFragmentManager.findFragmentByTag(it.tag) as TabContainerFragment
            }
        }
        val currentTab = savedInstanceState?.getString(KEY_CURRENT_TAB_TAG)
            ?.let { lastTabTag -> Tab.values().firstOrNull { it.tag == lastTabTag } }
            ?: Tab.Home
        switchTabContainerFragment(currentTab)
    }

    private fun switchTabContainerFragment(tab: Tab) {
        currentTab = tab
        val ft = supportFragmentManager.beginTransaction()

        currentContainerFragment?.let {
            ft.hide(it)
        }

        var fragment: TabContainerFragment? = tabContainerMap[tab]
        if (fragment == null) {
            fragment = TabContainerFragment.newInstance(tab)
            ft.add(R.id.fragment_container, fragment, tab.tag)
            tabContainerMap[tab] = fragment
        } else {
            ft.show(fragment)
        }

        ft.commitAllowingStateLoss()
        currentContainerFragment = fragment
        supportFragmentManager.executePendingTransactions()
    }

    override fun execute(command: Navigator.Command) {
        when (command) {
            is Navigator.Command.PushFragment -> {
                currentContainerFragment?.pushFragment(command)
            }
            is Navigator.Command.PopFragment -> {
                currentContainerFragment?.popFragment()
            }
            is Navigator.Command.LaunchActivity -> {
                val resultCode = command.requestCode ?: run {
                    startActivity(command.intent)
                    return
                }
                startActivityForResult(command.intent, resultCode)
            }
        }
    }
}
