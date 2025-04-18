package com.example.newsapp.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.common.extension.LinearSpacingItemDecoration
import com.example.newsapp.core.base.BaseSimpleAdapter
import com.example.newsapp.core.base.BaseViewHolder2
import com.example.newsapp.databinding.ItemContainerArticleBinding

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
class ContainerArticleAdapter : BaseSimpleAdapter<ArticleAdapter>() {

    override fun getItemViewType(position: Int): Int = R.layout.item_container_article

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder2<ArticleAdapter> = ViewHolder(
        ItemContainerArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    inner class ViewHolder(val binding: ItemContainerArticleBinding) :
        BaseViewHolder2<ArticleAdapter>(binding = binding) {
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

        override fun bindData(data: ArticleAdapter) {
            binding.rvContainer.adapter = data
        }
    }
}