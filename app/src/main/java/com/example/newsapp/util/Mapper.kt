package com.example.newsapp.util

import com.example.newsapp.data.local.entity.ArticleEntity
import com.example.newsapp.data.local.entity.SourceEntity
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Source

fun ArticleEntity.toModel(): Article = Article(
    author = this.author.orEmpty(),
    content = this.content.orEmpty(),
    description = this.description.orEmpty(),
    publishedAt = this.publishedAt.orEmpty(),
    source = this.source.toModel(),
    title = this.title.orEmpty(),
    url = this.url.orEmpty(),
    urlToImage = this.urlToImage.orEmpty()
)

fun SourceEntity.toModel(): Source = Source(
    id = this.id,
    name = this.name
)

fun Article.toEntity(): ArticleEntity = ArticleEntity(

    author = this.author.orEmpty(),
    content = this.content.orEmpty(),
    description = this.description.orEmpty(),
    publishedAt = this.publishedAt.orEmpty(),
    source = this.source.toEntity(),
    title = this.title.orEmpty(),
    url = this.url.orEmpty(),
    urlToImage = this.urlToImage.orEmpty()
)

fun Source.toEntity(): SourceEntity = SourceEntity(
    id = this.id,
    name = this.name
)