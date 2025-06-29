package com.example.newsapp.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.usecases.news.NewsUseCases
import com.example.newsapp.util.DataState
import com.example.newsapp.util.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    var bookmarkState by mutableStateOf<DataState<Unit>>(DataState.Success(Unit))
        private set
    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    fun onEvent(event: DetailEvent){
        when(event){
            is DetailEvent.UpsertDeleteArticle -> {

//                viewModelScope.launch {
//                    val article = newsUseCases.getArticleByUrl(event.article.url)
//                    if (article == null) {
//                        upsertArticle(event.article)
//                    } else {
//                        deleteArticle(event.article)
//                    }
//                }

                viewModelScope.launch {
                    bookmarkState = DataState.Loading(true)
                    delay(2000)
                    try {
                        val article = newsUseCases.getArticleByUrl(event.article.url)
                        if (article == null) {
                            bookmarkState = DataState.Success(Unit)
                            upsertArticle(event.article)
                        } else {
                            bookmarkState = DataState.Success(Unit)
                            deleteArticle(event.article)
                        }
                    } catch (e: Exception) {
                        bookmarkState = DataState.Response(
                            uiComponent = UIComponent.Toast("Something went wrong"),
                            error = e
                        )
                    } finally {
                        bookmarkState = DataState.Loading(false)
                    }
                }
            }

            is DetailEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article)
        sideEffect = UIComponent.Toast("Article Bookmarked")
    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article)
        sideEffect = UIComponent.Toast("Article Unbookmarked")
    }
}