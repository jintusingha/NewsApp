package com.jintu.news_app.domain.usecase

import com.jintu.news_app.domain.model.NewsArticle
import com.jintu.news_app.domain.repository.NewsRepository
import javax.inject.Inject

class BookmarkArticleUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(article: NewsArticle) {
        newsRepository.bookmarkArticle(article)
    }
}