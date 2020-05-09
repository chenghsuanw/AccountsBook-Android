package com.example.accountsbook.util

import android.app.Activity
import androidx.fragment.app.Fragment

inline fun <reified T : Any> Activity.extraRequired(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    return@lazy requireNotNull(value as? T ?: default) { key }
}

inline fun <reified T : Any> Fragment.extraRequired(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    return@lazy requireNotNull(value as? T ?: default) { key }
}
