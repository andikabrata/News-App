package com.example.newsapp.feature.detail_list_news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.newsapp.common.extension.loadImage
import com.example.newsapp.core.base.BaseListAdapter
import com.example.newsapp.core.base.BaseViewHolder2
import com.example.newsapp.databinding.ItemDetailListBlogBinding
import com.example.newsapp.feature.main.adapter.diffutil.BlogDiffutil
import com.example.newsapp.feature.main.model.ResultBlog

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class DetailListBlogAdapter : BaseListAdapter<ResultBlog, DetailListBlogAdapter.ViewHolder>(
    diffUtil = BlogDiffutil()
) {
    override val isIncludeFooter: Boolean
        get() = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailListBlogAdapter.ViewHolder = ViewHolder(
        ItemDetailListBlogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    inner class ViewHolder(private val binding: ItemDetailListBlogBinding) :
        BaseViewHolder2<ResultBlog>(binding = binding) {
        override fun bindData(data: ResultBlog) {
            binding.apply {
                ivThumbnail.loadImage(data.image_url)
            }
        }
    }

}