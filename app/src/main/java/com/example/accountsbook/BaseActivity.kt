package com.example.accountsbook

import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    open val toolbarTitle: String = ""

    private var toolBarTitleTv: TextView? = null

    protected fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar) ?: return
        setSupportActionBar(toolbar)
        LayoutInflater.from(this).inflate(R.layout.toolbar_content, toolbar)
        toolBarTitleTv = toolbar.findViewById(R.id.toolbar_content_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        resetToolbarTitle()
    }

    protected fun resetToolbarTitle() {
        toolBarTitleTv?.text = toolbarTitle
    }

    fun setHomeIndicator(@DrawableRes drawableRes: Int) {
        supportActionBar?.setHomeAsUpIndicator(drawableRes)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
