package com.shikherverma.themingarchitecture

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import android.support.v7.preference.SwitchPreferenceCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPreferences.getBoolean("pref_key_theme", false)) setTheme(R.style.AppThemeNight)
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }

    override fun onBackPressed() = NavUtils.navigateUpFromSameTask(this)

    class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onCreatePreferences(bundle: Bundle?, s: String?) {
            addPreferencesFromResource(R.xml.pref_settings)
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            setSummary(sharedPreferences, "pref_key_theme")
            setSummary(sharedPreferences, "pref_key_name")
            setSummary(sharedPreferences, "pref_key_email")
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            setSummary(sharedPreferences, key)
            if (key == "pref_key_theme") {
                activity.recreate()
            }
        }

        private fun setSummary(sharedPreferences: SharedPreferences, key: String) {
            val preference = findPreference(key)
            if (preference is SwitchPreferenceCompat) {
                if (sharedPreferences.getBoolean(key, false))
                    preference.setSummary("Enabled")
                else
                    preference.setSummary("Disabled")
            } else {
                preference.summary = sharedPreferences.getString(key, "")
            }
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }
    }
}
