package com.example.newsapp.common.extension

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
object Utils {
    fun isNotNull(list: List<*>?): Boolean = list != null && list.isNotEmpty()

    fun Int.dp(): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )
        .toInt()

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}