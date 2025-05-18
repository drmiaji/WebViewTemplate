package com.drmiaji.webviewtemplate.utils

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.drmiaji.webviewtemplate.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<ListPreference>("theme_mode")?.setOnPreferenceChangeListener { _, newValue ->
            ThemeUtils.saveThemeMode(requireContext(), newValue as String)
            true
        }
    }
}