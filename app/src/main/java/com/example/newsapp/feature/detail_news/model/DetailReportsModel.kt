package com.example.newsapp.feature.detail_news.model

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
data class DetailReportsModel (
    val authors: List<AuthorReports>,
    val id: Int,
    val image_url: String,
    val news_site: String,
    val published_at: String,
    val summary: String,
    val title: String,
    val updated_at: String,
    val url: String
)

data class AuthorReports(
    val name: String,
    val socials: SocialsReports
)

data class SocialsReports(
    val bluesky: String,
    val instagram: String,
    val linkedin: String,
    val mastodon: String,
    val x: String,
    val youtube: String
)