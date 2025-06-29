package com.example.newsapp.domain.usecases.news.bookmark

import com.example.newsapp.data.local.dao.NewsDao
import com.example.newsapp.domain.model.Article
import com.example.newsapp.util.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetArticles(
    private val newsDao: NewsDao
) {
    operator fun invoke(): Flow<List<Article>> {
        return newsDao.getArticles().map { entities ->
            entities.map {
                it.toModel()
            }
        }
    }
}