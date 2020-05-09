package com.example.accountsbook.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.accountsbook.BaseFragment
import com.example.accountsbook.R
import com.example.accountsbook.util.extraRequired

class TabContainerFragment : BaseFragment() {

    companion object {

        private const val ARG_TAB_TAG = "ARG_TAB_TAG"

        fun newInstance(tab: Tab): TabContainerFragment {
            return TabContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TAB_TAG, tab.tag)
                }
            }
        }
    }

    private val tab: Tab by lazy {
        val tabTag by extraRequired<String>(ARG_TAB_TAG)
        Tab.values().first { it.tag == tabTag }
    }

    interface FragmentChangedListener {

        fun onFragmentChanged(fragment: BaseFragment)
    }

    private var fragmentChangedListener: FragmentChangedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentChangedListener = context as FragmentChangedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomFragmentTag = "${tab.tag}_bottom"
        if (childFragmentManager.findFragmentByTag(bottomFragmentTag) == null) {
            pushFragment(
                Navigator.Command.PushFragment.Builder(tab.fragmentCreateDelegate.invoke())
                    .setTag(bottomFragmentTag)
                    .build()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isHidden) {
            notifyFragmentChanged()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        topFragment?.onHiddenChanged(hidden)
        if (!hidden) {
            notifyFragmentChanged()
        }
    }

    override fun onBackPressed(): Boolean {
        if (activity == null || !canPopFragment()) {
            return false
        }
        if (topFragment?.onBackPressed() == true) {
            return true
        }
        popFragment()
        return true
    }

    fun pushFragment(command: Navigator.Command.PushFragment) {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.vg_tab_container, command.fragment, command.tag)
            addToBackStack(command.tag)
            commitAllowingStateLoss()
        }
        childFragmentManager.executePendingTransactions()
        notifyFragmentChanged()
    }

    fun popFragment() {
        if (canPopFragment()) {
            childFragmentManager.popBackStack()
            childFragmentManager.executePendingTransactions()
            notifyFragmentChanged()
        }
    }

    fun canPopFragment(): Boolean {
        return childFragmentManager.backStackEntryCount > 1
    }

    private fun clearBackStack() {
        if (canPopFragment() && !isStateSaved) {
            val first: FragmentManager.BackStackEntry = childFragmentManager.getBackStackEntryAt(1)
            childFragmentManager.popBackStackImmediate(
                first.id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            notifyFragmentChanged()
        }
    }

    private val topFragment: BaseFragment?
        get() {
            var resultFragment: BaseFragment? = null
            if (childFragmentManager.backStackEntryCount > 0) {
                val entry = childFragmentManager.getBackStackEntryAt(
                    childFragmentManager.backStackEntryCount - 1
                )
                resultFragment = childFragmentManager.findFragmentByTag(entry.name) as BaseFragment
            }
            return resultFragment
        }

    private fun notifyFragmentChanged() {
        topFragment?.let {
            fragmentChangedListener?.onFragmentChanged(it)
        }
    }
}
