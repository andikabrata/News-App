package com.example.newsapp.feature.detail_list_news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.common.extension.loadImage
import com.example.newsapp.core.base.BaseListAdapter
import com.example.newsapp.core.base.BaseViewHolder2
import com.example.newsapp.databinding.ItemDetailListArticlesBinding
import com.example.newsapp.feature.main.adapter.diffutil.ArticleDiffutil
import com.example.newsapp.feature.main.model.ResultArticle

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class DetailListArticleAdapter : BaseListAdapter<ResultArticle, DetailListArticleAdapter.ViewHolder>(
    diffUtil = ArticleDiffutil()
) {
    private var listFiltered: List<ResultArticle> = ArrayList()
    private var tempListFiltered: List<ResultArticle> = ArrayList()

    override val isIncludeFooter: Boolean
        get() = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailListArticleAdapter.ViewHolder = ViewHolder(
        ItemDetailListArticlesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    inner class ViewHolder(private val binding: ItemDetailListArticlesBinding) :
        BaseViewHolder2<ResultArticle>(binding = binding) {
        override fun bindData(data: ResultArticle) {
            binding.apply {
                ivThumbnail.loadImage(data.image_url)
                tvTitle.text = data.title
                val launchAdapter = LaunchAdapter()
                rvValueLaunches.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = launchAdapter
                }
                val providerLaunchesList: List<String> = data.launches.map { it.provider }
                launchAdapter.submitList(providerLaunchesList)

                val eventAdapter = EventAdapter()
                rvValueEvent.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = eventAdapter
                }
                val providerEventList: List<String> = data.events.map { it.provider }
                eventAdapter.submitList(providerEventList)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        listFiltered = currentList

        listFiltered = if (query.isEmpty()) {
            tempListFiltered
        } else {
            listFiltered.filter { it.title.contains(query, ignoreCase = true) }
        }

        submitList(listFiltered)
        notifyDataSetChanged()
    }
}