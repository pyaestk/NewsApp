package com.example.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertArticle(articleEntity: ArticleEntity)

    @Delete
    suspend fun deleteArticle(articleEntity: ArticleEntity)

    @Query("SELECT * FROM Article")
    fun getArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM Article WHERE url=:url")
    suspend fun getArticleByUrl(url: String): ArticleEntity?
}