package com.drmiaji.webviewtemplate.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.drmiaji.webviewtemplate.MainActivity

object ThemeUtils {
    private const val PREF_NAME = "theme_pref"
    private const val KEY_THEME_MODE = "theme_mode"
    const val THEME_LIGHT = "light"
    const val THEME_DARK = "dark"
    const val THEME_SYSTEM = "system"

    fun applyTheme(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        when (prefs.getString(KEY_THEME_MODE, "system")) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun saveThemeMode(context: Context, mode: String) {
        val validModes = setOf(THEME_LIGHT, THEME_DARK, THEME_SYSTEM)
        if (mode !in validModes) return

        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit { putString(KEY_THEME_MODE, mode) }
        applyTheme(context)
        restartApp(context)
    }

    private fun restartApp(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}