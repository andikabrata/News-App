package com.example.newsapp.feature.detail_list_news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.newsapp.common.extension.loadImage
import com.example.newsapp.core.base.BaseListAdapter
import com.example.newsapp.core.base.BaseViewHolder2
import com.example.newsapp.databinding.ItemDetailListReportsBinding
import com.example.newsapp.feature.main.adapter.diffutil.ReportsDiffutil
import com.example.newsapp.feature.main.model.ResultReports

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class DetailListReportsAdapter : BaseListAdapter<ResultReports, DetailListReportsAdapter.ViewHolder>(
    diffUtil = ReportsDiffutil()
) {
    override val isIncludeFooter: Boolean
        get() = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailListReportsAdapter.ViewHolder = ViewHolder(
        ItemDetailListReportsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    inner class ViewHolder(private val binding: ItemDetailListReportsBinding) :
        BaseViewHolder2<ResultReports>(binding = binding) {
        override fun bindData(data: ResultReports) {
            binding.apply {
                ivThumbnail.loadImage(data.image_url)
            }
        }
    }

}