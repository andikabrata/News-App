package com.example.newsapp.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.common.extension.loadImage
import com.example.newsapp.core.base.BaseListAdapter
import com.example.newsapp.core.base.BaseViewHolder2
import com.example.newsapp.databinding.ItemArticlesBinding
import com.example.newsapp.feature.main.adapter.diffutil.ArticleDiffutil
import com.example.newsapp.feature.main.model.ResultArticle

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
class ArticleAdapter(
    private val onItemClick: (resultArticle: ResultArticle) -> Unit
) : BaseListAdapter<ResultArticle, ArticleAdapter.ViewHolder>(
    diffUtil = ArticleDiffutil()
) {
    override val isIncludeFooter: Boolean
        get() = false


    override fun getItemViewType(position: Int): Int = R.layout.item_articles

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder = ViewHolder(
        ItemArticlesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    inner class ViewHolder(private val binding: ItemArticlesBinding) :
        BaseViewHolder2<ResultArticle>(binding = binding) {
        override fun bindData(data: ResultArticle) {
            binding.root.setOnClickListener {
                onItemClick.invoke(data)
            }
            binding.ivThumbnail.loadImage(data.image_url)
        }
    }

}