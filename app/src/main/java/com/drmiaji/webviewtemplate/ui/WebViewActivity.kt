package com.drmiaji.webviewtemplate.ui

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import com.drmiaji.webviewtemplate.R
import com.drmiaji.webviewtemplate.activity.BaseActivity

class WebViewActivity : BaseActivity() {

    override fun getLayoutResource() = R.layout.activity_webview

    override fun onActivityReady(savedInstanceState: Bundle?) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = intent.getStringExtra("title") ?: "Reading"
        }

        val webView = findViewById<WebView>(R.id.webview)
        val fileName = intent.getStringExtra("fileName") ?: "chapter1.html"

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = false
        webView.loadUrl("file:///android_asset/contents/topics/$fileName")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}