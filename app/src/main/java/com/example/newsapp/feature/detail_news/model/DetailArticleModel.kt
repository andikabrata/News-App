package com.example.newsapp.feature.detail_news.model

/**
 * @author Andika Bratadirja
 * @date 18/04/2025
 */
data class DetailArticleModel(
    val authors: List<Author>,
    val events: List<Event>,
    val featured: Boolean,
    val id: Int,
    val image_url: String,
    val launches: List<Launches>,
    val news_site: String,
    val published_at: String,
    val summary: String,
    val title: String,
    val updated_at: String,
    val url: String
)

data class Author(
    val name: String,
    val socials: Socials
)

data class Event(
    val event_id: Int,
    val provider: String
)

data class Launches(
    val launch_id: String,
    val provider: String
)

data class Socials(
    val bluesky: String,
    val instagram: String,
    val linkedin: String,
    val mastodon: String,
    val x: String,
    val youtube: String
)
