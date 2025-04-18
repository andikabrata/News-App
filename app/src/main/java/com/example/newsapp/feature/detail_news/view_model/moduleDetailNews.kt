package com.example.newsapp.feature.detail_news.view_model

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
val moduleDetailNews = module {
    viewModel {
        DetailNewsViewModel(get(), get())
    }
}