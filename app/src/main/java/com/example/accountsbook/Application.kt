package com.example.accountsbook

import android.app.Application
import com.example.accountsbook.util.ContextRegistry
import java.lang.ref.WeakReference

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextRegistry.applicationContextRef = WeakReference(this)
    }
}
