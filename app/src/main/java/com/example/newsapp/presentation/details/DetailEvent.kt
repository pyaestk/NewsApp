package com.example.newsapp.presentation.details

import com.example.newsapp.domain.model.Article

sealed class DetailEvent {
    data class UpsertDeleteArticle(val article: Article): DetailEvent()

    object RemoveSideEffect: DetailEvent()
}