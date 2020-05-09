package com.example.accountsbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.accountsbook.navigation.ToolbarConfig

abstract class BaseFragment : Fragment() {

    open val toolbarConfig: ToolbarConfig? = null

    open fun onBackPressed(): Boolean {
        return false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }
}
