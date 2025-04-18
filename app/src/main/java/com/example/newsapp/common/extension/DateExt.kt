package com.example.newsapp.common.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
@SuppressLint("SimpleDateFormat")
fun Date.toStringDate(pattern: String = "dd MMM yyyy"): String {
    val format = SimpleDateFormat(pattern, Locale("en", "us"))
    return format.format(this)
}

fun String.converDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val date = inputFormat.parse(this)
    val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
    return outputFormat.format(date ?: "")
}