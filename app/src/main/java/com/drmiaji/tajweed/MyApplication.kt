package com.drmiaji.tajweed

import android.app.Application
import com.drmiaji.tajweed.utils.ThemeUtils

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemeUtils.applyTheme(this)
    }
}