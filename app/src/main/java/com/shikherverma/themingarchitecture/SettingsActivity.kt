package com.shikherverma.themingarchitecture

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import android.support.v7.preference.SwitchPreferenceCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }

    class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

        override fun onCreatePreferences(bundle: Bundle?, s: String?) {
            addPreferencesFromResource(R.xml.pref_settings)
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            onSharedPreferenceChanged(sharedPreferences, "pref_key_theme")
            onSharedPreferenceChanged(sharedPreferences, "pref_key_name")
            onSharedPreferenceChanged(sharedPreferences, "pref_key_email")
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            val preference = findPreference(key)
            if (preference is SwitchPreferenceCompat) {
                if(sharedPreferences.getBoolean(key, false))
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
