package com.jintu.news_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jintu.news_app.data.Mappers.toUiModel
import com.jintu.news_app.domain.usecase.GetBookmarkedArticlesUseCase
import com.jintu.news_app.presentation.model.NewsArticleUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject




sealed class BookmarksUiState {
    object Loading : BookmarksUiState()
    data class Success(val articles: List<NewsArticleUiModel>) : BookmarksUiState()
    data class Error(val message: String) : BookmarksUiState()
}

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getBookmarkedArticlesUseCase: GetBookmarkedArticlesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookmarksUiState>(BookmarksUiState.Loading)
    val uiState: StateFlow<BookmarksUiState> = _uiState.asStateFlow()

    init {
        fetchBookmarkedArticles()
    }

    private fun fetchBookmarkedArticles() {
        viewModelScope.launch {
            try {
                _uiState.value = BookmarksUiState.Loading
                val articles = getBookmarkedArticlesUseCase.execute()
                val uiModels = articles.map { it.toUiModel() }
                _uiState.value = BookmarksUiState.Success(uiModels)
            } catch (e: Exception) {
                _uiState.value = BookmarksUiState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
