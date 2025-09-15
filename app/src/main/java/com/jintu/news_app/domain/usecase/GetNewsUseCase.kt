package com.jintu.news_app.domain.usecase

import com.jintu.news_app.domain.model.NewsArticle
import com.jintu.news_app.domain.repository.NewsRepository
import javax.inject.Inject


class GetNewsUseCase @Inject constructor (private val repository: NewsRepository){


    suspend fun execute(): List<NewsArticle>{
        return repository.getNews()

    }
}