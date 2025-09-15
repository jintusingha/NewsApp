package com.jintu.news_app.presentation

import com.jintu.news_app.domain.model.NewsArticle
import com.jintu.news_app.presentation.model.NewsArticleUiModel

sealed class UiEvents {
    data class NavigateToDetail(val article: NewsArticleUiModel) : UiEvents()
}