package com.example.newsapp.feature.detail_list_news.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.common.extension.LinearSpacingItemDecoration
import com.example.newsapp.common.extension.ViewState
import com.example.newsapp.core.base.BaseVMActivity
import com.example.newsapp.databinding.ActivityDetailListNewsBinding
import com.example.newsapp.feature.detail_list_news.adapter.DetailListArticleAdapter
import com.example.newsapp.feature.detail_list_news.adapter.DetailListBlogAdapter
import com.example.newsapp.feature.detail_list_news.adapter.DetailListReportsAdapter
import com.example.newsapp.feature.detail_list_news.view_model.DetailListNewsViewModel

class DetailListNewsActivity : BaseVMActivity<DetailListNewsViewModel, ActivityDetailListNewsBinding>() {
    private var newsType: String = ""

    companion object {
        var ARTICLE = "article"
        var BLOG = "blog"
        var REPORTS = "reports"

        fun startActivity(context: Context, newsType: String) {
            val intent = Intent(context, DetailListNewsActivity::class.java)
            intent.putExtra("newsType", newsType)
            context.startActivity(intent)
        }
    }


    private lateinit var detailListArticleAdapter: DetailListArticleAdapter
    private lateinit var detailListBlogAdapter: DetailListBlogAdapter
    private lateinit var detailListReportsAdapter: DetailListReportsAdapter

    override fun getViewModel() = DetailListNewsViewModel::class.java

    override fun getViewBinding(): ActivityDetailListNewsBinding {
        return ActivityDetailListNewsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        newsType = intent.getStringExtra("newsType") ?: ""
        initAdapter()
        initViewModel()

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                detailListArticleAdapter.filter(text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    override fun observerViewModel() {
        viewModel.listDetailArticleLiveData.onResult { state ->
            when (state) {
                is ViewState.Loading -> {}

                is ViewState.Success -> {
                    detailListArticleAdapter.submitList(state.data.results)
                }

                is ViewState.Failed -> {}

                else -> {}
            }

        }

        viewModel.listDetailBlogLiveData.onResult { state ->
            when (state) {
                is ViewState.Loading -> {}

                is ViewState.Success -> {
                    detailListBlogAdapter.submitList(state.data.results)
                }

                is ViewState.Failed -> {}

                else -> {}
            }

        }

        viewModel.listDetailReportsLiveData.onResult { state ->
            when (state) {
                is ViewState.Loading -> {}

                is ViewState.Success -> {
                    detailListReportsAdapter.submitList(state.data.results)
                }

                is ViewState.Failed -> {}

                else -> {}
            }

        }
    }

    private fun initViewModel() {
        when (newsType) {
            ARTICLE -> viewModel.getDetailListArticles()
            BLOG -> viewModel.getDetailListBlog()
            REPORTS -> viewModel.getDetailListReports()
        }
    }

    private fun initAdapter() {
        detailListArticleAdapter = DetailListArticleAdapter()
        detailListBlogAdapter = DetailListBlogAdapter()
        detailListReportsAdapter = DetailListReportsAdapter()

        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        manager.apply {
            recycleChildrenOnDetach = true
            isItemPrefetchEnabled = true
            initialPrefetchItemCount = 6
        }

        val sharedRecyclerViewPool = RecyclerView.RecycledViewPool()

        binding.rvNews.apply {
            layoutManager = manager
            adapter = when (newsType) {
                ARTICLE -> detailListArticleAdapter
                BLOG -> detailListBlogAdapter
                REPORTS -> detailListReportsAdapter
                else -> detailListArticleAdapter
            }
            addItemDecoration(
                LinearSpacingItemDecoration(
                    this@DetailListNewsActivity, R.dimen.spacing_7, false
                )
            )
            clearOnScrollListeners()
            setRecycledViewPool(sharedRecyclerViewPool)
        }
    }

}