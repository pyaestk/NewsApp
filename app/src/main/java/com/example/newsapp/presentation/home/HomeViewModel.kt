package com.example.newsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    val news: Flow<PagingData<Article>> = newsUseCases.getNews(
        sources = listOf("bbc-news", "abc-news", "al-jazeera-english")
    ).cachedIn(viewModelScope)

}