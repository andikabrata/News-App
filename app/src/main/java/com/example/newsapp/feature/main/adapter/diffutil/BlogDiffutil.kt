package com.example.newsapp.feature.main.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapp.feature.main.model.ResultBlog

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class BlogDiffutil: DiffUtil.ItemCallback<ResultBlog>()  {
    override fun areItemsTheSame(
        oldItem: ResultBlog,
        newItem: ResultBlog
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ResultBlog,
        newItem: ResultBlog
    ): Boolean = oldItem == newItem
}