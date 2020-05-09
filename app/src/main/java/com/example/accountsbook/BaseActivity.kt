package com.example.accountsbook

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.accountsbook.navigation.ToolbarConfig
import com.example.accountsbook.util.statusBarHeight

abstract class BaseActivity : AppCompatActivity() {

    open val toolbarTitle: String = ""

    private var toolBarTitleTv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        val toolbar = findViewById<Toolbar>(R.id.toolbar) ?: return
        toolbar.setPadding(0, statusBarHeight(), 0, 0)
        setSupportActionBar(toolbar)
        LayoutInflater.from(this).inflate(R.layout.toolbar_content, toolbar)
        toolBarTitleTv = toolbar.findViewById(R.id.toolbar_content_title)
        toolBarTitleTv?.text = toolbarTitle
    }

    protected fun setupToolbar(toolbarConfig: ToolbarConfig) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar) ?: return
        setSupportActionBar(toolbar)
        LayoutInflater.from(this).inflate(R.layout.toolbar_content, toolbar)
        toolBarTitleTv = toolbar.findViewById(R.id.toolbar_content_title)
        toolBarTitleTv?.text = toolbarConfig.title
        if (toolbarConfig.homeIcon != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(toolbarConfig.homeIcon)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
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

    private fun setStatusBar() {
        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }
}
