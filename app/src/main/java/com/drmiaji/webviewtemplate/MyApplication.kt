package com.drmiaji.webviewtemplate

import android.app.Application
import com.drmiaji.webviewtemplate.utils.ThemeUtils

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeUtils.applyTheme(this)
    }
}