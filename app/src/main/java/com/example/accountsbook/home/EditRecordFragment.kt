package com.example.accountsbook.home

import com.example.accountsbook.BaseFragment
import com.example.accountsbook.R
import com.example.accountsbook.navigation.ToolbarConfig

class EditRecordFragment : BaseFragment() {

    companion object {
        fun newInstance() = EditRecordFragment()
    }

    override val toolbarConfig: ToolbarConfig?
        get() = ToolbarConfig(
            title = "紀錄",
            homeIcon = R.drawable.ic_back
        )
}
