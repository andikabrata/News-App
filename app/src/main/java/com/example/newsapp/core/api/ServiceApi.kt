package com.example.newsapp.core.api

import com.example.newsapp.feature.detail_news.model.DetailArticleModel
import com.example.newsapp.feature.detail_news.model.DetailBlogModel
import com.example.newsapp.feature.detail_news.model.DetailReportsModel
import com.example.newsapp.feature.main.model.ArticleModel
import com.example.newsapp.feature.main.model.BlogModel
import com.example.newsapp.feature.main.model.ReportModel
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
interface ServiceApi {
    @GET("articles/?has_event=true&has_launch=true")
    suspend fun getArticle(): ArticleModel

    @GET("blogs/")
    suspend fun getBlog(): BlogModel

    @GET("reports/")
    suspend fun getReports(): ReportModel


    @GET("articles/{id}/")
    suspend fun getDetailArticle(
        @Path("id") id: Int?
    ): DetailArticleModel

    @GET("blogs/{id}/")
    suspend fun getDetailBlog(
        @Path("id") id: Int?
    ): DetailBlogModel

    @GET("reports/{id}/")
    suspend fun getDetailReports(
        @Path("id") id: Int?
    ): DetailReportsModel
}