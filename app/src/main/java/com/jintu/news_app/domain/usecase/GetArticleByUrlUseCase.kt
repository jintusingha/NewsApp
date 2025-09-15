package com.jintu.news_app.domain.usecase

import com.jintu.news_app.domain.model.NewsArticle
import com.jintu.news_app.domain.repository.NewsRepository
import javax.inject.Inject

class GetArticleByUrlUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(url: String): NewsArticle {

        return newsRepository.getArticleByUrl(url)
    }
}