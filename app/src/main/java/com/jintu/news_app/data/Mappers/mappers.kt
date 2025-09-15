package com.jintu.news_app.data.Mappers

import com.jintu.news_app.data.local.entity.ArticleEntity
import com.jintu.news_app.data.remote.Article
import com.jintu.news_app.domain.model.NewsArticle
import com.jintu.news_app.presentation.model.NewsArticleUiModel


fun Article.toNewsArticle(): NewsArticle{
    return NewsArticle(
        title=this.title?: "No Title",
        description=this.description?:"No Description",
        imageUrl = this.urlToImage?:"no image",
        url=this.url?:"no url",
        isBookmarked = false
    )
}

fun NewsArticle.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        url = this.url,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        isBookmarked = this.isBookmarked
    )
}

fun ArticleEntity.toNewsArticle(): NewsArticle {
    return NewsArticle(
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        url = this.url,
        isBookmarked = this.isBookmarked
    )
}

fun NewsArticle.toUiModel(): NewsArticleUiModel {
    return NewsArticleUiModel(
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        url = this.url,
        isBookmarked=this.isBookmarked
    )
}

fun NewsArticleUiModel.toDomainModel(): NewsArticle {
    return NewsArticle(
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        url = this.url,
        isBookmarked = true
    )
}