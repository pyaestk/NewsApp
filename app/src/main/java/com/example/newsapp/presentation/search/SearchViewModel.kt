package com.example.newsapp.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.SearchNews -> {
                val articles = newsUseCases.searchNews(
                    searchQuery = _state.value.searchQuery,
                    sources = listOf("bbc-news", "abc-news", "al-jazeera-english")
                ).cachedIn(viewModelScope)

                _state.value = _state.value.copy(articles = articles)
            }
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(searchQuery = event.searchQuery)
            }
        }

    }

}