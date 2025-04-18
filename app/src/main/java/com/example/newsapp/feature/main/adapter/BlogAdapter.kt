package com.example.newsapp.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.common.extension.loadImage
import com.example.newsapp.core.base.BaseListAdapter
import com.example.newsapp.core.base.BaseViewHolder2
import com.example.newsapp.databinding.ItemBlogBinding
import com.example.newsapp.feature.main.adapter.diffutil.BlogDiffutil
import com.example.newsapp.feature.main.model.ResultBlog

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class BlogAdapter(
    private val onItemClick: (resultBlog: ResultBlog) -> Unit
) : BaseListAdapter<ResultBlog, BlogAdapter.ViewHolder>(
    diffUtil = BlogDiffutil()
) {
    override val isIncludeFooter: Boolean
        get() = false


    override fun getItemViewType(position: Int): Int = R.layout.item_articles

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogAdapter.ViewHolder = ViewHolder(
        ItemBlogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    inner class ViewHolder(private val binding: ItemBlogBinding) :
        BaseViewHolder2<ResultBlog>(binding = binding) {
        override fun bindData(data: ResultBlog) {
            binding.root.setOnClickListener {
                onItemClick.invoke(data)
            }
            binding.ivThumbnail.loadImage(data.image_url)
        }
    }

}