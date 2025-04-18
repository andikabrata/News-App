package com.example.newsapp.feature.register.view_model

import com.example.newsapp.core.base.BaseViewModel
import com.example.newsapp.core.session.PreferenceProvider
import com.example.newsapp.feature.login.model.Auth

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class RegisterViewModel(
    private val preference: PreferenceProvider,
) : BaseViewModel() {
    fun setPreference(auth: Auth) {
        preference.setAuthPreferences(auth)
    }
}