package com.example.newsapp.domain.usecases.news.bookmark

import com.example.newsapp.data.local.dao.NewsDao
import com.example.newsapp.domain.model.Article
import com.example.newsapp.util.toEntity

class UpsertArticle(
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(article: Article) {
        newsDao.upsertArticle(article.toEntity())
    }
}