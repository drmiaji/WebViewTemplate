package com.drmiaji.webviewtemplate.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import android.os.Bundle
import com.drmiaji.webviewtemplate.activity.BaseActivity
import com.drmiaji.webviewtemplate.R

class WebViewActivity : BaseActivity() {

    override fun getLayoutResource() = R.layout.activity_webview

    override fun onActivityReady(savedInstanceState: Bundle?) {
        val webView = findViewById<WebView>(R.id.webview)
        val fileName = intent.getStringExtra("fileName") ?: "chapter1.html"

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = false
        webView.loadUrl("file:///android_asset/contents/chapters/$fileName")
    }
}