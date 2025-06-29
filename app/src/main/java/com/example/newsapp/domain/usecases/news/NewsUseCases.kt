package com.example.newsapp.domain.usecases.news

import com.example.newsapp.domain.usecases.news.bookmark.DeleteArticle
import com.example.newsapp.domain.usecases.news.bookmark.GetArticles
import com.example.newsapp.domain.usecases.news.bookmark.UpsertArticle

data class NewsUseCases(
    val getNews: GetNews,
    val searchNews: SearchNews,

    val getArticles: GetArticles,
    val upsertArticle: UpsertArticle,
    val deleteArticle: DeleteArticle,

    val getArticleByUrl: GetArticleByUrl

)