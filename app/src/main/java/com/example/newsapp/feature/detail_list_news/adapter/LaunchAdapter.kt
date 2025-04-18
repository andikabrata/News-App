package com.example.newsapp.feature.detail_list_news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemLaunchesBinding
import com.example.newsapp.feature.main.model.Launches

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class LaunchAdapter : RecyclerView.Adapter<LaunchAdapter.ViewHolder>() {
    private val items = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<String>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLaunchesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.tvLaunches.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLaunchesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}