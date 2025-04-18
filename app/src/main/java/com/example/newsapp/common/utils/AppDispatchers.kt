package com.example.newsapp.common.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
class AppDispatchers(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher
)