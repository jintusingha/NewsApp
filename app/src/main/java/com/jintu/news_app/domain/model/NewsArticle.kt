package com.jintu.news_app.domain.model

import retrofit2.http.Url

data class NewsArticle(

    val title:String,
    val description:String,
    val imageUrl: String,
    val url:String,
    val isBookmarked: Boolean = false

)


