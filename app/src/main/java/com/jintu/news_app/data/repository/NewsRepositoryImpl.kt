package com.jintu.news_app.data.repository

import com.google.gson.internal.GsonBuildConfig
import com.jintu.news_app.BuildConfig
import com.jintu.news_app.data.Mappers.toArticleEntity
import com.jintu.news_app.data.Mappers.toNewsArticle
import com.jintu.news_app.data.local.dao.ArticleDao
import com.jintu.news_app.data.local.entity.ArticleEntity
import com.jintu.news_app.data.remote.NewsApiService
import com.jintu.news_app.domain.model.NewsArticle
import com.jintu.news_app.domain.repository.NewsRepository
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(private val api: NewsApiService,private val localArticleDao: ArticleDao) : NewsRepository {


    override suspend fun getNews(): List<NewsArticle> {
        return try {
            val apikey = BuildConfig.NEWS_API_KEY
            val response = api.getTopNews("us", apikey)
            val articles = response.articles.map { it.toNewsArticle() }
            val articleEntities = articles.map { it.toArticleEntity() }


            localArticleDao.insertAll(articleEntities)

            articles

        } catch (e: Exception) {

            val cachedArticles = localArticleDao.getAllArticles()
            return cachedArticles.map { it.toNewsArticle() }
        }
    }
    override suspend fun getArticleByUrl(url: String): NewsArticle {
        val articleEntity = localArticleDao.getArticleByUrl(url)
        return articleEntity?.toNewsArticle()
            ?: throw Exception("Article not found for url: $url")
    }
    override suspend fun bookmarkArticle(article: NewsArticle) {
        val articleEntity = article.toArticleEntity()
        localArticleDao.insertArticle(articleEntity)
    }

    override suspend fun getBookmarkedArticles(): List<NewsArticle> {
        val bookmarkedEntities = localArticleDao.getBookmarkedArticles()
        return bookmarkedEntities.map { it.toNewsArticle() }
    }

}



