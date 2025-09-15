package com.jintu.news_app.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jintu.news_app.data.Mappers.toDomainModel
import com.jintu.news_app.data.Mappers.toUiModel
import com.jintu.news_app.domain.usecase.BookmarkArticleUseCase
import com.jintu.news_app.domain.usecase.GetArticleByUrlUseCase
import com.jintu.news_app.domain.usecase.GetBookmarkedArticlesUseCase
import com.jintu.news_app.presentation.model.NewsArticleUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class DetailUiState {
    object Loading : DetailUiState()
    object Success : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getArticleByUrlUseCase: GetArticleByUrlUseCase,
    private val bookmarkArticleUseCase: BookmarkArticleUseCase,

    savedStateHandle: SavedStateHandle
) : ViewModel() {


    sealed class DetailUiState {
        object Loading : DetailUiState()
        data class Success(val article: NewsArticleUiModel) : DetailUiState()
        data class Error(val message: String) : DetailUiState()
    }

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    val articleUrl: String = savedStateHandle.get<String>("articleUrl") ?: ""

    private var currentArticle: NewsArticleUiModel? = null

    init {
        fetchArticleByUrl(articleUrl)
    }

    private fun fetchArticleByUrl(url: String) {
        viewModelScope.launch {
            try {
                _uiState.value = DetailUiState.Loading
                val fullArticle = getArticleByUrlUseCase.execute(url)
                Log.d("DetailViewModel", "Article fetched: $fullArticle")
                val uiModel = fullArticle.toUiModel()
                currentArticle = uiModel
                _uiState.value = DetailUiState.Success(uiModel)
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Failed to find article.")
            }
        }
    }
    fun onBookmarkClicked() {
        viewModelScope.launch {
            currentArticle?.let { uiModel ->

                val domainModel = uiModel.toDomainModel()


                bookmarkArticleUseCase.execute(domainModel)
                Log.d("DetailViewModel", "Article bookmarked: ${uiModel.title}")

            }
        }
    }


    fun onPageFinished() {
        currentArticle?.let {
            _uiState.value = DetailUiState.Success(it)
        }
    }

    fun onReceivedError(message: String) {
        _uiState.value = DetailUiState.Error(message)
    }

    fun onRefreshClicked() {
        fetchArticleByUrl(articleUrl)
    }
}