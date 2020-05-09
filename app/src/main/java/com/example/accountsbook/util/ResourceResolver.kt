package com.example.accountsbook.util

import android.util.TypedValue
import androidx.core.content.ContextCompat

private fun context() = ContextRegistry.applicationContext
private fun resources() = context().resources

fun Int.color(): Int {
    return ContextCompat.getColor(context(), this)
}

fun Int.dp(): Float {
    val displayMetrics = resources().displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), displayMetrics)
}

fun Int.pixelSize(): Int {
    return resources().getDimensionPixelSize(this)
}

fun statusBarHeight(): Int {
    val id = resources().getIdentifier("status_bar_height", "dimen", "android")
    return if (id > 0) resources().getDimensionPixelSize(id) else 0
}
