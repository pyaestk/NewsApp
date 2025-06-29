package com.example.newsapp.domain.usecases.news

import com.example.newsapp.data.local.dao.NewsDao
import com.example.newsapp.domain.model.Article
import com.example.newsapp.util.toEntity
import com.example.newsapp.util.toModel

class GetArticleByUrl(
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(url: String): Article? {
        return newsDao.getArticleByUrl(url)?.toModel()
    }
}