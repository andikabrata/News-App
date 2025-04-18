package com.example.newsapp.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.core.base.BaseViewHolder2
import com.example.newsapp.databinding.ItemTitleBlogBinding

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
class TitleBlogAdapter(private val callback: () -> Unit) : RecyclerView.Adapter<BaseViewHolder2<Nothing>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder2<Nothing> = ViewHolder(
        ItemTitleBlogBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemViewType(position: Int): Int = R.layout.item_title_blog

    override fun onBindViewHolder(holder: BaseViewHolder2<Nothing>, position: Int) {}

    override fun getItemCount(): Int = 1

    inner class ViewHolder(val binding: ItemTitleBlogBinding) :
        BaseViewHolder2<Nothing>(binding = binding) {
        init {
            binding.tvSeeMore.setOnClickListener {
                callback.invoke()
            }
        }

        override fun bindData(data: Nothing) {}
    }
}