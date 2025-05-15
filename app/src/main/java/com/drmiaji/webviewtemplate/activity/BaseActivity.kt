package com.drmiaji.webviewtemplate.activity

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.drmiaji.webviewtemplate.R

@Suppress("DEPRECATION")
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getThemeId())
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        adjustSystemUI()
        onActivityReady(savedInstanceState)
    }

    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    protected abstract fun onActivityReady(savedInstanceState: Bundle?)

    @ColorRes
    open fun getStatusBarColor(): Int = android.R.color.white

    open fun getThemeId(): Int = R.style.Theme_WebviewTemplate

    private fun adjustSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        window.statusBarColor = ContextCompat.getColor(this, getStatusBarColor())
        window.navigationBarColor = ContextCompat.getColor(this, getStatusBarColor())

    }

    fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        currentFocus?.let { view ->
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
