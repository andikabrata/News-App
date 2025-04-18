package com.example.newsapp.feature.main.view

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.common.extension.ViewState
import com.example.newsapp.core.base.BaseVMActivity
import com.example.newsapp.core.session.PreferenceProvider
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.feature.detail_list_news.view.DetailListNewsActivity
import com.example.newsapp.feature.detail_news.view.DetailNewsActivity
import com.example.newsapp.feature.detail_news.view.DetailNewsActivity.Companion.ARTICLE
import com.example.newsapp.feature.detail_news.view.DetailNewsActivity.Companion.BLOG
import com.example.newsapp.feature.detail_news.view.DetailNewsActivity.Companion.REPORTS
import com.example.newsapp.feature.login.view.LoginActivity
import com.example.newsapp.feature.main.adapter.ArticleAdapter
import com.example.newsapp.feature.main.adapter.BlogAdapter
import com.example.newsapp.feature.main.adapter.ContainerArticleAdapter
import com.example.newsapp.feature.main.adapter.ContainerBlogAdapter
import com.example.newsapp.feature.main.adapter.ContainerReportsAdapter
import com.example.newsapp.feature.main.adapter.ReportsAdapter
import com.example.newsapp.feature.main.adapter.TitleArticleAdapter
import com.example.newsapp.feature.main.adapter.TitleBlogAdapter
import com.example.newsapp.feature.main.adapter.TitleReportsAdapter
import com.example.newsapp.feature.main.view_model.MainViewModel
import org.koin.android.ext.android.inject
import java.util.Calendar

class MainActivity : BaseVMActivity<MainViewModel, ActivityMainBinding>() {
    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var titleArticleAdapter: TitleArticleAdapter
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var containerArticleAdapter: ContainerArticleAdapter
    private lateinit var titleBlogAdapter: TitleBlogAdapter
    private lateinit var blogAdapter: BlogAdapter
    private lateinit var containerBlogAdapter: ContainerBlogAdapter
    private lateinit var titleReportsAdapter: TitleReportsAdapter
    private lateinit var reportsAdapter: ReportsAdapter
    private lateinit var containerReportsAdapter: ContainerReportsAdapter

    private val mPreferenceProvider: PreferenceProvider by inject()

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getViewModel() = MainViewModel::class.java

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        scheduleAutoLogout(this)
        initAdapter()
        initViewModel()
        binding.apply {
            tvGreetings.text = getGreeting()
            tvUserName.text = mPreferenceProvider.getAuthPreferences().name
            ivLogout.setOnClickListener {
                mPreferenceProvider.clearAuthPreferences()
                LoginActivity.startActivity(this@MainActivity)
                finish()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observerViewModel() {
        viewModel.listArticleLiveData.onResult { state ->
            when (state) {
                is ViewState.Loading -> {}

                is ViewState.Success -> {
                    articleAdapter.submitList(state.data.results)
                    containerArticleAdapter.notifyDataSetChanged()
                }

                is ViewState.Failed -> {}

                else -> {}
            }

        }

        viewModel.listBlogLiveData.onResult { state ->
            when (state) {
                is ViewState.Loading -> {}

                is ViewState.Success -> {
                    blogAdapter.submitList(state.data.results)
                    containerBlogAdapter.notifyDataSetChanged()
                }

                is ViewState.Failed -> {}

                else -> {}
            }

        }

        viewModel.listReportLiveData.onResult { state ->
            when (state) {
                is ViewState.Loading -> {}

                is ViewState.Success -> {
                    reportsAdapter.submitList(state.data.results)
                    containerReportsAdapter.notifyDataSetChanged()
                }

                is ViewState.Failed -> {}

                else -> {}
            }

        }
    }

    private fun initViewModel() {
        viewModel.getArticles()
        viewModel.getBlog()
        viewModel.getReport()
    }

    private fun initAdapter() {
        titleArticleAdapter = TitleArticleAdapter {
            DetailListNewsActivity.startActivity(this, ARTICLE)
        }
        articleAdapter = ArticleAdapter {
            DetailNewsActivity.startActivity(this, it.id, ARTICLE)
        }
        containerArticleAdapter = ContainerArticleAdapter().apply {
            setData(articleAdapter)
        }
        titleBlogAdapter = TitleBlogAdapter {
            DetailListNewsActivity.startActivity(this, BLOG)
        }
        blogAdapter = BlogAdapter {
            DetailNewsActivity.startActivity(this, it.id, BLOG)
        }
        containerBlogAdapter = ContainerBlogAdapter().apply {
            setData(blogAdapter)
        }
        titleReportsAdapter = TitleReportsAdapter {
            DetailListNewsActivity.startActivity(this, REPORTS)
        }
        reportsAdapter = ReportsAdapter {
            DetailNewsActivity.startActivity(this, it.id, REPORTS)
        }
        containerReportsAdapter = ContainerReportsAdapter().apply {
            setData(reportsAdapter)
        }
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        manager.apply {
            recycleChildrenOnDetach = true
            isItemPrefetchEnabled = true
            initialPrefetchItemCount = 6
        }

        val config = ConcatAdapter.Config.Builder()
        config.setIsolateViewTypes(false)

        val sharedRecyclerViewPool = RecyclerView.RecycledViewPool()

        concatAdapter = ConcatAdapter(
            titleArticleAdapter,
            containerArticleAdapter,
            titleBlogAdapter,
            containerBlogAdapter,
            titleReportsAdapter,
            containerReportsAdapter
        )

        binding.recyclerView.apply {
            layoutManager = manager
            adapter = concatAdapter
            setRecycledViewPool(sharedRecyclerViewPool)
        }
    }

    private fun getGreeting(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 4..10 -> "Good Morning"
            in 11..14 -> "Good Afternoon"
            in 15..17 -> "Good Evening"
            in 18..23 -> "Good Night"
            else -> "Good Night"
        }
    }

    @SuppressLint("MissingPermission")
    fun scheduleAutoLogout(context: Context) {
        val intent = Intent(context, LogoutReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1001,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val triggerAt = System.currentTimeMillis() + (10_000) // 10 menit

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAt,
            pendingIntent
        )
    }
}