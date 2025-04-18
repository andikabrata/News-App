package com.example.newsapp.feature.main.view_model

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
val mainModule = module {
    viewModel {
        MainViewModel(get(), get())
    }
}