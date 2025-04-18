package com.example.newsapp.core.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
abstract class BaseViewHolder2<T>(
    binding: ViewBinding? = null,
    view: View? = null
) : RecyclerView.ViewHolder(
    binding?.root ?: view ?: throw IllegalStateException("Please use either binding or view")
) {
    abstract fun bindData(data: T)
}