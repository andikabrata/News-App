package com.example.newsapp.core.base

import androidx.recyclerview.widget.RecyclerView

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
abstract class BaseSimpleAdapter<T : RecyclerView.Adapter<out RecyclerView.ViewHolder>>
    : RecyclerView.Adapter<BaseViewHolder2<T>>() {

    private var data: T? = null

    fun setData(data: T) {
        this.data = data
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: BaseViewHolder2<T>, position: Int) {
        data?.let { holder.bindData(it) }
    }
}