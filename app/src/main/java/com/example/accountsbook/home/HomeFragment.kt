package com.example.accountsbook.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.accountsbook.BaseFragment
import com.example.accountsbook.R
import com.example.accountsbook.navigation.ToolbarConfig

class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val toolbarConfig: ToolbarConfig?
        get() = ToolbarConfig(
            title = "薪資帳戶"
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
