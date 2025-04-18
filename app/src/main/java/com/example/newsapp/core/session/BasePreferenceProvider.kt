package com.example.newsapp.core.session

import android.content.SharedPreferences

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
abstract class BasePreferenceProvider(val preference: SharedPreferences) {
    open fun setStringToPreference(key: String, value: String?) {
        preference.edit().putString(key, value).apply()
    }

    open fun getStringFromPreference(key: String): String? {
        return preference.getString(key, "")
    }

    open fun getStringOrNullFromPreference(key: String): String? {
        return preference.getString(key, null)
    }

    open fun setIntToPreference(key: String, value: Int) {
        preference.edit().putInt(key, value).apply()
    }

    open fun getIntFromPreference(key: String): Int {
        return preference.getInt(key, 0)
    }

    open fun setLongToPreference(key: String, value: Long) {
        preference.edit().putLong(key, value).apply()
    }

    open fun getLongFromPreference(key: String, defaultValue: Long): Long {
        return preference.getLong(key, defaultValue)
    }

    open fun setBooleanToPreference(key: String, value: Boolean) {
        preference.edit().putBoolean(key, value).apply()
    }

    open fun getBooleanFromPreference(
        key: String,
        defaultValue: Boolean? = false
    ): Boolean {

        return preference.getBoolean(key, defaultValue!!)
    }

    open fun clearPreferences(key: String) {
        preference.edit().remove(key).apply()
    }

    open fun clearPreferences() {
        preference.edit().clear().apply()
    }

    open fun deleteField(key: String) {
        preference.edit().remove(key).apply()
    }

    open fun getStringFromPreferenceWithNull(key: String): String? {
        return preference.getString(key, null)
    }
}