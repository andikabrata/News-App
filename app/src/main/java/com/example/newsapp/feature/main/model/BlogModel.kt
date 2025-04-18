package com.example.newsapp.feature.main.model

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
data class BlogModel(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<ResultBlog>
)

data class ResultBlog(
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

data class EventBlog(
    val event_id: Int,
    val provider: String
)

data class LaunchesBlog(
    val launch_id: String,
    val provider: String
)

data class AuthorBlog(
    val name: String,
    val socials: Any
)