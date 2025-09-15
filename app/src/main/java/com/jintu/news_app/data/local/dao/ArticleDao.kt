package com.jintu.news_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.jintu.news_app.data.local.entity.ArticleEntity

import com.jintu.news_app.domain.model.NewsArticle

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    @Query("SELECT * FROM articles WHERE url = :url")
    suspend fun getArticleByUrl(url: String): ArticleEntity?

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<ArticleEntity>


    @Query("SELECT * FROM articles WHERE isBookmarked = 1")
    suspend fun getBookmarkedArticles(): List<ArticleEntity>
}