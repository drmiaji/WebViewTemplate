package com.drmiaji.webviewtemplate.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import com.drmiaji.webviewtemplate.R
import com.drmiaji.webviewtemplate.activity.BaseActivity

class WebViewActivity : BaseActivity() {

    override fun getLayoutResource() = R.layout.activity_webview

    @SuppressLint("SetJavaScriptEnabled")
    override fun onActivityReady(savedInstanceState: Bundle?) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = intent.getStringExtra("title") ?: "Reading"
        }

        val webView = findViewById<WebView>(R.id.webview)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        val fileName = intent.getStringExtra("fileName") ?: "chapter1.html"

        // Load base.html template
        val baseHtml = assets.open("contents/base.html").bufferedReader().use { it.readText() }
        // Load content HTML
        val contentHtml = assets.open("contents/topics/$fileName").bufferedReader().use { it.readText() }

        // Inject content into template
        val fullHtml = baseHtml
            .replace("{{CONTENT}}", contentHtml)
            .replace("{{THEME}}", "light") // or "dark" for dark mode
            .replace("{{STYLE}}", "")       // leave blank if style.css handles styling

        webView.loadDataWithBaseURL(
            "file:///android_asset/contents/",
            fullHtml,
            "text/html",
            "utf-8",
            null
        )
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