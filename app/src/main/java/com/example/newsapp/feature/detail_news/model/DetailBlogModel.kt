package com.example.newsapp.feature.detail_news.model

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
data class DetailBlogModel(
    val authors: List<AuthorBlog>,
    val events: List<EventBlog>,
    val featured: Boolean,
    val id: Int,
    val image_url: String,
    val launches: List<LaunchesBlog>,
    val news_site: String,
    val published_at: String,
    val summary: String,
    val title: String,
    val updated_at: String,
    val url: String
)

data class AuthorBlog(
    val name: String,
    val socials: SocialsBlog
)

data class EventBlog(
    val event_id: Int,
    val provider: String
)

data class LaunchesBlog(
    val launch_id: String,
    val provider: String
)


data class SocialsBlog(
    val bluesky: String,
    val instagram: String,
    val linkedin: String,
    val mastodon: String,
    val x: String,
    val youtube: String
)
