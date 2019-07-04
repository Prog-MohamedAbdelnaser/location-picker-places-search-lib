package com.softtech.android_structure.data.sources.local

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener

// use commit() instead of apply(), because sometimes we kill the application process immediately
// which will prevent apply() to finish
@SuppressLint("ApplySharedPref")
class AppPreference(private val preference: SharedPreferences) {

    fun putString(key: String, value: String) {
        preference.edit().putString(key, value).apply()
    }

    fun putStringWithCommit(key: String, value: String) {
        preference.edit().putString(key, value).commit()
    }

    /**
     * get saved string with given `key` if key is not found or its value is `null`
     * returns `defaultValue`
     */
    fun getString(key: String, defaultValue: String?): String? =
            preference.getString(key, defaultValue) ?: defaultValue

    fun registerOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
        preference.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener) {
        preference.unregisterOnSharedPreferenceChangeListener(listener)
    }

}