package com.drmiaji.webviewtemplate.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.drmiaji.webviewtemplate.utils.SettingsFragment
import com.drmiaji.webviewtemplate.utils.ThemeUtils

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeUtils.applyTheme(this) // Apply selected theme
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }
}