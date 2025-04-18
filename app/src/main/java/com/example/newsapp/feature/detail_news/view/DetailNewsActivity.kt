package com.example.newsapp.feature.detail_news.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.example.newsapp.common.extension.ViewState
import com.example.newsapp.common.extension.loadImage
import com.example.newsapp.core.base.BaseVMActivity
import com.example.newsapp.databinding.ActivityDetailNewsBinding
import com.example.newsapp.feature.detail_news.view_model.DetailNewsViewModel
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

class DetailNewsActivity : BaseVMActivity<DetailNewsViewModel, ActivityDetailNewsBinding>() {
    private var newsId: Int = 0
    private var newsType: String = ""

    companion object {
        var ARTICLE = "article"
        var BLOG = "blog"
        var REPORTS = "reports"

        fun startActivity(context: Context, newsId: Int, newsType: String) {
            val intent = Intent(context, DetailNewsActivity::class.java)
            intent.putExtra("newsId", newsId)
            intent.putExtra("newsType", newsType)
            context.startActivity(intent)
        }
    }

    override fun getViewModel() = DetailNewsViewModel::class.java

    override fun getViewBinding(): ActivityDetailNewsBinding {
        return ActivityDetailNewsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        newsId = intent.getIntExtra("newsId", 0)
        newsType = intent.getStringExtra("newsType") ?: ""
        initViewModel()
    }

    override fun observerViewModel() {
        viewModel.detailArticleLiveData.onResult { state ->
            when (state) {
                is ViewState.Loading -> {}

                is ViewState.Success -> {
                    state.data.let { data ->
                        binding.apply {
                            ivThumbnail.loadImage(data.image_url)
                            tvTitle.text = data.title
                            tvAuthor.text = data.authors[0].name
                            tvTime.text = formatDate(data.published_at)
                            tvDescription.text = getFirtsSentance(data.summary)
                        }

                    }
                }

                is ViewState.Failed -> {}

                else -> {}
            }

        }

        viewModel.detailBlogLiveData.onResult { state ->
            when (state) {
                is ViewState.Loading -> {}

                is ViewState.Success -> {
                    state.data.let { data ->
                        binding.apply {
                            ivThumbnail.loadImage(data.image_url)
                            tvTitle.text = data.title
                            tvAuthor.text = data.authors[0].name
                            tvTime.text = formatDate(data.published_at)
                            tvDescription.text = getFirtsSentance(data.summary)
                        }

                    }
                }

                is ViewState.Failed -> {}

                else -> {}
            }

        }

        viewModel.detailReportsLiveData.onResult { state ->
            when (state) {
                is ViewState.Loading -> {}

                is ViewState.Success -> {
                    state.data.let { data ->
                        binding.apply {
                            ivThumbnail.loadImage(data.image_url)
                            tvTitle.text = data.title
                            tvAuthor.text = if (data.authors.isNotEmpty()) data.authors[0].name else "Author Not Found"
                            tvTime.text = formatDate(data.published_at)
                            tvDescription.text = getFirtsSentance(data.summary)
                        }

                    }
                }

                is ViewState.Failed -> {}

                else -> {}
            }

        }
    }

    private fun initViewModel() {
        when (newsType) {
            ARTICLE -> viewModel.getDetailArticles(newsId = newsId)
            BLOG -> viewModel.getDetailBlog(newsId = newsId)
            REPORTS -> viewModel.getDetailReports(newsId = newsId)
        }
    }


    private fun formatDate(input: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val zonedDateTime = ZonedDateTime.parse(input) // parsing ISO format
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale("id", "ID"))
            return zonedDateTime.format(formatter)
        } else {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val formatter = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("id", "ID"))
            val date = parser.parse(input)
            return formatter.format(date!!)
        }
    }

    private fun getFirtsSentance(summary: String): String {
        val index = summary.indexOf(".")
        return if (index != -1) summary.substring(0, index + 1) else summary
    }

}