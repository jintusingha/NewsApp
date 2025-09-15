package com.jintu.news_app.domain.repository

import com.jintu.news_app.domain.model.NewsArticle


interface NewsRepository {
    suspend fun getNews(): List<NewsArticle>
    suspend fun getArticleByUrl(url: String): NewsArticle
    suspend fun bookmarkArticle(article: NewsArticle)
    suspend fun getBookmarkedArticles(): List<NewsArticle>
}