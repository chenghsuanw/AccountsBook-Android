package com.example.accountsbook.util

import android.content.Context
import java.lang.ref.WeakReference

object ContextRegistry {
    var applicationContextRef: WeakReference<Context>? = null

    @JvmStatic
    val applicationContext: Context
        get() = applicationContextRef?.get() ?: error("applicationContext is null")
}
