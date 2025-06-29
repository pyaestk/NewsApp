package com.example.newsapp.presentation.bookmark

import com.example.newsapp.domain.model.Article

sealed class BookMarkEvent {
    data class SaveArticle(val article: Article): BookMarkEvent()
}