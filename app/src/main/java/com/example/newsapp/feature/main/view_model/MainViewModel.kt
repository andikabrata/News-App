package com.example.newsapp.feature.main.view_model

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
 * @date 17/04/2025
 */
class MainViewModel(
    private val serviceUtil: ServiceApi,
    private val dispatchers: AppDispatchers,
) : BaseViewModel() {
    val listArticleLiveData = MutableLiveData<ViewState<ArticleModel>>()
    val listBlogLiveData = MutableLiveData<ViewState<BlogModel>>()
    val listReportLiveData = MutableLiveData<ViewState<ReportModel>>()

    fun getArticles() {
        listArticleLiveData.setLoading()
        launchOnUi(
            dispatcher = dispatchers,
            onRequest = {
                val result = serviceUtil.getArticle()
                listArticleLiveData.setSuccess(result)
            },
            onError = {
                listArticleLiveData.setError(it)
            }
        )
    }

    fun getBlog() {
        listBlogLiveData.setLoading()
        launchOnUi(
            dispatcher = dispatchers,
            onRequest = {
                val result = serviceUtil.getBlog()
                listBlogLiveData.setSuccess(result)
            },
            onError = {
                listBlogLiveData.setError(it)
            }
        )
    }


    fun getReport() {
        listReportLiveData.setLoading()
        launchOnUi(
            dispatcher = dispatchers,
            onRequest = {
                val result = serviceUtil.getReports()
                listReportLiveData.setSuccess(result)
            },
            onError = {
                listReportLiveData.setError(it)
            }
        )
    }
}