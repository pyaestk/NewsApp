package com.example.newsapp.data.local.database

import androidx.room.Database
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.util.NewsTypeConverter
import com.example.newsapp.data.local.dao.NewsDao
import com.example.newsapp.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsDao

}