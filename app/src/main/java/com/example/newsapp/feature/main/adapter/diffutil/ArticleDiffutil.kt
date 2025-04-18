package com.example.newsapp.feature.main.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapp.feature.main.model.ResultArticle

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
class ArticleDiffutil : DiffUtil.ItemCallback<ResultArticle>()  {
    override fun areItemsTheSame(
        oldItem: ResultArticle,
        newItem: ResultArticle
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ResultArticle,
        newItem: ResultArticle
    ): Boolean = oldItem == newItem
}