package com.example.newsapp.core.di

import com.example.newsapp.common.utils.AppDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
val coreModule = module {
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
}