package com.jintu.news_app.presentation

import com.jintu.news_app.domain.model.NewsArticle
import com.jintu.news_app.presentation.model.NewsArticleUiModel

sealed class NewsUiState{
    object Loading: NewsUiState()
    data class Success(val news:List<NewsArticleUiModel>): NewsUiState()
    data class Error(val message:String): NewsUiState()
}

