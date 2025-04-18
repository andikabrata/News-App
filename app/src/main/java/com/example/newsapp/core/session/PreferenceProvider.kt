package com.example.newsapp.core.session

import android.content.SharedPreferences
import com.example.newsapp.feature.login.model.Auth

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
class PreferenceProvider(pref: SharedPreferences) : BasePreferenceProvider(preference = pref) {

    fun setAuthPreferences(auth: Auth?) {
        auth ?: return
        setStringToPreference(SharedPreferencesKey.NAME, auth.name)
        setStringToPreference(SharedPreferencesKey.PICTURE, auth.picture)
    }

    fun getAuthPreferences(): Auth {
        val auth = Auth()
        auth.name = getStringFromPreference(SharedPreferencesKey.NAME)
        auth.picture = getStringFromPreference(SharedPreferencesKey.PICTURE)
        return auth
    }

    fun clearAuthPreferences() {
        setStringToPreference(SharedPreferencesKey.NAME, null)
        setStringToPreference(SharedPreferencesKey.PICTURE, null)
    }

    fun isLogin(): Boolean {
        val userId = getStringFromPreference(SharedPreferencesKey.NAME)
        return userId != ""
    }
}