package com.example.newsapp.feature.main.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapp.feature.main.model.ResultReports

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class ReportsDiffutil : DiffUtil.ItemCallback<ResultReports>() {
    override fun areItemsTheSame(
        oldItem: ResultReports,
        newItem: ResultReports
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ResultReports,
        newItem: ResultReports
    ): Boolean = oldItem == newItem
}