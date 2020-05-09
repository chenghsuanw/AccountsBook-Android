package com.example.accountsbook.navigation

import androidx.annotation.IdRes
import com.example.accountsbook.BaseFragment
import com.example.accountsbook.R
import com.example.accountsbook.home.HomeFragment
import java.util.Locale

enum class Tab(
    @IdRes val menuId: Int,
    val fragmentCreateDelegate: () -> BaseFragment
) {

    Home(
        menuId = R.id.navigation_home,
        fragmentCreateDelegate = {
            HomeFragment.newInstance()
        }
    );

    val tag: String
        get() = "TAG_${this.name.toUpperCase(Locale.US)}"
}
