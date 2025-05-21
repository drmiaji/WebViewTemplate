package com.drmiaji.webviewtemplate.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.net.toUri
import com.drmiaji.webviewtemplate.R
import com.drmiaji.webviewtemplate.activity.About
import com.drmiaji.webviewtemplate.activity.BaseActivity
import com.drmiaji.webviewtemplate.activity.SettingsActivity
import com.drmiaji.webviewtemplate.utils.ThemeUtils

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
        val iconColor = ContextCompat.getColor(this, R.color.toolbar_icon_color)
        toolbar.navigationIcon?.let { drawable ->
            val wrapped = DrawableCompat.wrap(drawable).mutate()
            DrawableCompat.setTint(wrapped, iconColor)
            toolbar.navigationIcon = wrapped
        }

        val webView = findViewById<WebView>(R.id.webview)
        // Add progress indicator
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar?.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar?.visibility = View.GONE
            }
        }
//        webView.webViewClient = WebViewClient()
//        webView.settings.javaScriptEnabled = true

        // Enable zoom functionality
        webView.settings.apply {
            javaScriptEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false // Hide the default zoom controls
            useWideViewPort = true // Enables viewport meta tags
            loadWithOverviewMode = true // Fits content to screen

            // Optional: Increase text size if needed
            // textZoom = 120 // Set text zoom percentage (100 is normal)
        }

        // Check if we're loading an external URL or internal file
        val externalUrl = intent.getStringExtra("url")

        if (externalUrl != null) {
            // Load external URL
            webView.loadUrl(externalUrl)
        } else {
            // Existing code for loading internal HTML files
            val fileName = intent.getStringExtra("fileName") ?: "chapter1.html"
            val themeMode = ThemeUtils.getCurrentThemeMode(this)
            val themeClass = when (themeMode) {
                ThemeUtils.THEME_DARK -> "dark"
                ThemeUtils.THEME_LIGHT -> "light"
                else -> {
                    val nightModeFlags = resources.configuration.uiMode and
                            android.content.res.Configuration.UI_MODE_NIGHT_MASK
                    if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES)
                        "dark" else "light"
                }
            }

//        val fileName = intent.getStringExtra("fileName") ?: "chapter1.html"
//
//        // Determine the current theme mode
//        val themeMode = ThemeUtils.getCurrentThemeMode(this)
//        val themeClass = when (themeMode) {
//            ThemeUtils.THEME_DARK -> "dark"
//            ThemeUtils.THEME_LIGHT -> "light"
//            else -> {
//                // Fallback to system
//                val nightModeFlags = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
//                if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) "dark" else "light"
//            }
//        }

            // Load base.html template
            val baseHtml = assets.open("contents/base.html").bufferedReader().use { it.readText() }
            // Load content HTML
            val contentHtml =
                assets.open("contents/topics/$fileName").bufferedReader().use { it.readText() }

            // Inject content and theme into template
            val fullHtml = baseHtml
                .replace("{{CONTENT}}", contentHtml)
                .replace("{{THEME}}", themeClass)
                .replace("{{STYLE}}", "")

            webView.loadDataWithBaseURL(
                "file:///android_asset/contents/",
                fullHtml,
                "text/html",
                "utf-8",
                null
            )
        }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        menu.findItem(R.id.action_search)?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.share -> {
                val myIntent = Intent(Intent.ACTION_SEND)
                myIntent.setType("text/plain")
                val shareSub: String? = getString(R.string.share_subject)
                val shareBody: String? = getString(R.string.share_message)
                myIntent.putExtra(Intent.EXTRA_TEXT, shareSub)
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(myIntent, "Share using!"))
            }
            R.id.more_apps -> {
                val moreApp = Intent(Intent.ACTION_VIEW)
                moreApp.setData("https://play.google.com/store/apps/details?id=com.drmiaji.webviewtemplate".toUri())
                startActivity(moreApp)
            }
            R.id.action_about_us -> {
                startActivity(Intent(this, About::class.java))
            }
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}