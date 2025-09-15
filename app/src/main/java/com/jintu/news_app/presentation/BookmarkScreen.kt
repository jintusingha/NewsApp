package com.jintu.news_app.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jintu.news_app.domain.model.NewsArticle
import java.net.URLEncoder

@Composable
fun BookmarkScreen(
    viewModel: BookmarksViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is BookmarksUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is BookmarksUiState.Success -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.articles) { article ->

                    NewsArticleItem(
                        article = article,
                        onItemClick = { clickedArticle ->

                            if (!clickedArticle.url.isNullOrBlank()) {
                                
                                val encodedUrl = URLEncoder.encode(clickedArticle.url, "UTF-8")
                                navController.navigate("news_detail/$encodedUrl")
                            }
                        }
                    )
                }
            }
        }
        is BookmarksUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${state.message}")
            }
        }
    }
}