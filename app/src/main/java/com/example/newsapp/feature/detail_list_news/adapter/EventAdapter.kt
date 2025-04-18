package com.example.newsapp.feature.detail_list_news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemEventBinding

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    private val items = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<String>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.tvEvent.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}