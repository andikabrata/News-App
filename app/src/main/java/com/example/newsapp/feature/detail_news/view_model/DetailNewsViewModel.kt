package com.example.newsapp.feature.detail_news.view_model

import androidx.lifecycle.MutableLiveData
import com.example.newsapp.common.extension.ViewState
import com.example.newsapp.common.extension.setError
import com.example.newsapp.common.extension.setLoading
import com.example.newsapp.common.extension.setSuccess
import com.example.newsapp.common.utils.AppDispatchers
import com.example.newsapp.core.api.ServiceApi
import com.example.newsapp.core.base.BaseViewModel
import com.example.newsapp.feature.detail_news.model.DetailArticleModel
import com.example.newsapp.feature.detail_news.model.DetailBlogModel
import com.example.newsapp.feature.detail_news.model.DetailReportsModel

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
class DetailNewsViewModel(
    private val serviceUtil: ServiceApi,
    private val dispatchers: AppDispatchers,
) : BaseViewModel() {
    val detailArticleLiveData = MutableLiveData<ViewState<DetailArticleModel>>()
    val detailBlogLiveData = MutableLiveData<ViewState<DetailBlogModel>>()
    val detailReportsLiveData = MutableLiveData<ViewState<DetailReportsModel>>()

    fun getDetailArticles(newsId: Int) {
        detailArticleLiveData.setLoading()
        launchOnUi(
            dispatcher = dispatchers,
            onRequest = {
                val result = serviceUtil.getDetailArticle(id = newsId)
                detailArticleLiveData.setSuccess(result)
            },
            onError = {
                detailArticleLiveData.setError(it)
            }
        )
    }

    fun getDetailBlog(newsId: Int) {
        detailBlogLiveData.setLoading()
        launchOnUi(
            dispatcher = dispatchers,
            onRequest = {
                val result = serviceUtil.getDetailBlog(id = newsId)
                detailBlogLiveData.setSuccess(result)
            },
            onError = {
                detailBlogLiveData.setError(it)
            }
        )
    }

    fun getDetailReports(newsId: Int) {
        detailReportsLiveData.setLoading()
        launchOnUi(
            dispatcher = dispatchers,
            onRequest = {
                val result = serviceUtil.getDetailReports(id = newsId)
                detailReportsLiveData.setSuccess(result)
            },
            onError = {
                detailReportsLiveData.setError(it)
            }
        )
    }
}