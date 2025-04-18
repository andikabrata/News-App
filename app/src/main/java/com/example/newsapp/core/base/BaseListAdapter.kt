package com.example.newsapp.core.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.newsapp.common.extension.Utils

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
abstract class BaseListAdapter<T, VH : BaseViewHolder2<T>>(
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffUtil) {
    abstract val isIncludeFooter: Boolean

    companion object {
        internal const val DATA_TYPE = 0
        internal const val FOOTER_TYPE = 1
        internal const val ADS_TYPE = 2
    }

    override fun getItemViewType(position: Int): Int {
        return DATA_TYPE
    }

    override fun getItemCount(): Int = if (Utils.isNotNull(currentList)) {
        if (isIncludeFooter) currentList.size + 1
        else currentList.size
    } else {
        0
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(getItem(position))
    }
}