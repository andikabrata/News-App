package com.example.newsapp.feature.detail_list_news.view_model

import androidx.lifecycle.MutableLiveData
import com.example.newsapp.common.extension.ViewState
import com.example.newsapp.common.extension.setError
import com.example.newsapp.common.extension.setLoading
import com.example.newsapp.common.extension.setSuccess
import com.example.newsapp.common.utils.AppDispatchers
import com.example.newsapp.core.api.ServiceApi
import com.example.newsapp.core.base.BaseViewModel
import com.example.newsapp.feature.main.model.ArticleModel
import com.example.newsapp.feature.main.model.BlogModel
import com.example.newsapp.feature.main.model.ReportModel

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class DetailListNewsViewModel(
    private val serviceUtil: ServiceApi,
    private val dispatchers: AppDispatchers,
) : BaseViewModel() {
    val listDetailArticleLiveData = MutableLiveData<ViewState<ArticleModel>>()
    val listDetailBlogLiveData = MutableLiveData<ViewState<BlogModel>>()
    val listDetailReportsLiveData = MutableLiveData<ViewState<ReportModel>>()

    fun getDetailListArticles() {
        listDetailArticleLiveData.setLoading()
        launchOnUi(
            dispatcher = dispatchers,
            onRequest = {
                val result = serviceUtil.getArticle()
                listDetailArticleLiveData.setSuccess(result)
            },
            onError = {
                listDetailArticleLiveData.setError(it)
            }
        )
    }

    fun getDetailListBlog() {
        listDetailBlogLiveData.setLoading()
        launchOnUi(
            dispatcher = dispatchers,
            onRequest = {
                val result = serviceUtil.getBlog()
                listDetailBlogLiveData.setSuccess(result)
            },
            onError = {
                listDetailBlogLiveData.setError(it)
            }
        )
    }

    fun getDetailListReports() {
        listDetailReportsLiveData.setLoading()
        launchOnUi(
            dispatcher = dispatchers,
            onRequest = {
                val result = serviceUtil.getReports()
                listDetailReportsLiveData.setSuccess(result)
            },
            onError = {
                listDetailReportsLiveData.setError(it)
            }
        )
    }
}