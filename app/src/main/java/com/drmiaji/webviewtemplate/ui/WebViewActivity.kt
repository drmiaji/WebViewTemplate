package com.drmiaji.webviewtemplate.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
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

        // Set custom font to the title
        setCustomFontToTitle(toolbar)

        val webView = findViewById<WebView>(R.id.webview)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        // Enable zoom functionality
        webView.settings.apply {
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false // Hide the default zoom controls
            useWideViewPort = true // Enables viewport meta tags
            loadWithOverviewMode = true // Fits content to screen

            // Optional: Increase text size if needed
            // textZoom = 120 // Set text zoom percentage (100 is normal)
        }

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

    /**
     * Sets a custom font to the toolbar title
     */
    private fun setCustomFontToTitle(toolbar: Toolbar) {
        // Get the title text view from the toolbar
        for (i in 0 until toolbar.childCount) {
            val view = toolbar.getChildAt(i)
            if (view is TextView && view.text == toolbar.title) {
                // Apply custom typeface
                val typeface = ResourcesCompat.getFont(this, R.font.solaimanlipi)
                view.typeface = typeface

                // Optional: you can also modify other text properties
                // view.textSize = 20f // in sp
                // view.setTextColor(ContextCompat.getColor(this, R.color.your_color))
                break
            }
        }
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