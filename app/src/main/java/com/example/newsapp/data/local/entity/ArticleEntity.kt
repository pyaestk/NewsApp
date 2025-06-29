package com.example.newsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Article")
data class ArticleEntity(

    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceEntity,
    val title: String,

    @PrimaryKey
    val url: String,
    val urlToImage: String
)
data class SourceEntity(
    val id: String,
    val name: String
)