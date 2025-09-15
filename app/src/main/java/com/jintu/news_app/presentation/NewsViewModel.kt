package com.jintu.news_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jintu.news_app.domain.model.NewsArticle
import com.jintu.news_app.domain.usecase.GetNewsUseCase
import com.jintu.news_app.presentation.model.NewsArticleUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject





@HiltViewModel
class NewsViewModel@Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
): ViewModel(){

    private val _uiState= MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState

    // can we not use stateflow directly

    private val _uiEvents = Channel<UiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()
   //  is init block less testable
    init {
        fetchNews()
    }

    private fun fetchNews(){
        viewModelScope.launch {
            try {
                _uiState.value= NewsUiState.Loading
                val newsList=getNewsUseCase.execute()
                _uiState.value= NewsUiState.Success(newsList.map {
                    NewsArticleUiModel(
                        title=it.title,
                        description = it.description,
                        imageUrl = it.imageUrl,
                        url=it.url,
                        isBookmarked = it.isBookmarked
                    )

                })
            }catch(e: Exception){
                _uiState.value= NewsUiState.Error(e.message?:"An unknown error occured")
            }
        }
    }


    fun onArticleClicked(article: NewsArticleUiModel) {
        viewModelScope.launch {
            _uiEvents.send(UiEvents.NavigateToDetail(article))
        }
    }
}













