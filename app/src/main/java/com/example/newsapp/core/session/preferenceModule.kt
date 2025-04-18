package com.example.newsapp.core.session

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.newsapp.NewsApplication
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
val preferenceModule = module {
    single { provideSettingsPreferences(androidApplication()) }
    single { PreferenceProvider(get()) }
}

private val PREFERENCES_FILE_KEY = NewsApplication::class.java.simpleName

private fun provideSettingsPreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)