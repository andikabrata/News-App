package com.example.newsapp.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.common.extension.loadImage
import com.example.newsapp.core.base.BaseListAdapter
import com.example.newsapp.core.base.BaseViewHolder2
import com.example.newsapp.databinding.ItemReportsBinding
import com.example.newsapp.feature.main.adapter.diffutil.ReportsDiffutil
import com.example.newsapp.feature.main.model.ResultReports

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class ReportsAdapter(
    private val onItemClick: (resultReports: ResultReports) -> Unit
) : BaseListAdapter<ResultReports, ReportsAdapter.ViewHolder>(
    diffUtil = ReportsDiffutil()
) {
    override val isIncludeFooter: Boolean
        get() = false


    override fun getItemViewType(position: Int): Int = R.layout.item_articles

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsAdapter.ViewHolder = ViewHolder(
        ItemReportsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    inner class ViewHolder(private val binding: ItemReportsBinding) :
        BaseViewHolder2<ResultReports>(binding = binding) {
        override fun bindData(data: ResultReports) {
            binding.root.setOnClickListener {
                onItemClick.invoke(data)
            }
            binding.ivThumbnail.loadImage(data.image_url)
        }
    }

}