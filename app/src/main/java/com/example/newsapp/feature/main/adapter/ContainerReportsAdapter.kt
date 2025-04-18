package com.example.newsapp.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.common.extension.LinearSpacingItemDecoration
import com.example.newsapp.core.base.BaseSimpleAdapter
import com.example.newsapp.core.base.BaseViewHolder2
import com.example.newsapp.databinding.ItemContainerReportsBinding

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class ContainerReportsAdapter : BaseSimpleAdapter<ReportsAdapter>() {
    override fun getItemViewType(position: Int): Int = R.layout.item_container_reports

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder2<ReportsAdapter> = ViewHolder(
        ItemContainerReportsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    inner class ViewHolder(val binding: ItemContainerReportsBinding) :
        BaseViewHolder2<ReportsAdapter>(binding = binding) {
        private val ctx = binding.root.context

        init {
            binding.rvContainer.apply {
                layoutManager =
                    LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(
                    LinearSpacingItemDecoration(
                        ctx, R.dimen.spacing_7, false
                    )
                )
            }
        }

        override fun bindData(data: ReportsAdapter) {
            binding.rvContainer.adapter = data
        }
    }

}