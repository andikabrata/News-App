package com.example.newsapp.core.di

import com.example.newsapp.core.session.preferenceModule
import com.example.newsapp.feature.detail_list_news.view_model.moduleDetailListNews
import com.example.newsapp.feature.detail_news.view_model.moduleDetailNews
import com.example.newsapp.feature.login.view_model.loginModule
import com.example.newsapp.feature.main.view_model.mainModule
import com.example.newsapp.feature.register.view_model.moduleRegister

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
val appComponent = listOf(
    coreModule,
    retrofitModule,
    preferenceModule,
    loginModule,
    moduleRegister,
    mainModule,
    moduleDetailNews,
    moduleDetailListNews
)