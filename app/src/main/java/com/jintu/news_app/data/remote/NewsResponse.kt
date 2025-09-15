package com.jintu.news_app.data.remote

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)